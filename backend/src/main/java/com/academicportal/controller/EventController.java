package com.academicportal.controller;

import com.academicportal.dto.EventDto;
import com.academicportal.dto.UserDto;
import com.academicportal.entity.Event;
import com.academicportal.entity.User;
import com.academicportal.service.AuthService;
import com.academicportal.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EventController {
    
    private final EventService eventService;
    private final AuthService authService;
    
    @GetMapping
    public ResponseEntity<List<EventDto>> getAllEvents() {
        List<EventDto> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventDto> getEventById(@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/creator/{createdById}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<EventDto>> getEventsByCreator(@PathVariable Long createdById) {
        UserDto currentUser = authService.getCurrentUser();
        
        // Teachers can only view their own events unless admin
        if (currentUser.getRole().equals(User.UserRole.TEACHER.toString()) && !currentUser.getId().equals(createdById)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        List<EventDto> events = eventService.getEventsByCreator(createdById);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/my")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<List<EventDto>> getMyEvents() {
        UserDto currentUser = authService.getCurrentUser();
        List<EventDto> events = eventService.getEventsByCreator(currentUser.getId());
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<EventDto>> getEventsByType(@PathVariable String type) {
        try {
            Event.EventType eventType = Event.EventType.valueOf(type.toUpperCase());
            List<EventDto> events = eventService.getEventsByType(eventType);
            return ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<List<EventDto>> getEventsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<EventDto> events = eventService.getEventsByDateRange(startDate, endDate);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/date/{eventDate}")
    public ResponseEntity<List<EventDto>> getEventsByDate(@PathVariable LocalDate eventDate) {
        List<EventDto> events = eventService.getEventsByDate(eventDate);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/range")
    public ResponseEntity<List<EventDto>> getEventsInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<EventDto> events = eventService.getEventsInRange(startDate, endDate);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/date/{date}/ordered")
    public ResponseEntity<List<EventDto>> getEventsByDateOrderByTime(@PathVariable LocalDate date) {
        List<EventDto> events = eventService.getEventsByDateOrderByTime(date);
        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<EventDto>> getEventsByPriority(@PathVariable String priority) {
        try {
            Event.Priority eventPriority = Event.Priority.valueOf(priority.toUpperCase());
            List<EventDto> events = eventService.getEventsByPriority(eventPriority);
            return ResponseEntity.ok(events);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/ordered")
    public ResponseEntity<List<EventDto>> getAllEventsOrderByPriorityAndDate() {
        List<EventDto> events = eventService.getAllEventsOrderByPriorityAndDate();
        return ResponseEntity.ok(events);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto eventDto) {
        UserDto currentUser = authService.getCurrentUser();
        eventDto.setCreatedById(currentUser.getId());
        
        EventDto createdEvent = eventService.createEvent(eventDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long id, @RequestBody EventDto eventDto) {
        EventDto updatedEvent = eventService.updateEvent(id, eventDto);
        return ResponseEntity.ok(updatedEvent);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}
