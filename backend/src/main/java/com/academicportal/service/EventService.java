package com.academicportal.service;

import com.academicportal.dto.EventDto;
import com.academicportal.entity.Event;
import com.academicportal.jdbc.JdbcEventRepository;
import com.academicportal.jdbc.JdbcUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final JdbcEventRepository eventRepository;
    private final JdbcUserRepository userRepository;

    public EventService(JdbcEventRepository eventRepository, JdbcUserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public List<EventDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<EventDto> getEventById(Long id) {
        Event event = eventRepository.findById(id);
        return event != null ? Optional.of(convertToDto(event)) : Optional.empty();
    }

    public List<EventDto> getEventsByCreator(Long createdById) {
        return eventRepository.findByCreatedById(createdById).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByType(Event.EventType type) {
        return eventRepository.findByType(type).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByDateRange(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findByEventDateBetween(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByDate(LocalDate eventDate) {
        return eventRepository.findByEventDate(eventDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsInRange(LocalDate startDate, LocalDate endDate) {
        return eventRepository.findEventsInRange(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByDateOrderByTime(LocalDate date) {
        return eventRepository.findByDateOrderByTime(date).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getEventsByPriority(Event.Priority priority) {
        return eventRepository.findByPriority(priority).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<EventDto> getAllEventsOrderByPriorityAndDate() {
        return eventRepository.findAllOrderByPriorityAndDate().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public EventDto createEvent(EventDto eventDto) {
        Event event = convertToEntity(eventDto);
        Event savedEvent = eventRepository.save(event);
        return convertToDto(savedEvent);
    }

    public EventDto updateEvent(Long id, EventDto eventDto) {
        Event existingEvent = eventRepository.findById(id);
        if (existingEvent == null) {
            throw new RuntimeException("Event not found");
        }

        // Update fields
        existingEvent.setTitle(eventDto.getTitle());
        existingEvent.setDescription(eventDto.getDescription());
        existingEvent.setEventDate(eventDto.getEventDate());
        existingEvent.setStartTime(eventDto.getStartTime());
        existingEvent.setEndTime(eventDto.getEndTime());
        existingEvent.setLocation(eventDto.getLocation());
        existingEvent.setType(eventDto.getType());
        existingEvent.setPriority(eventDto.getPriority());
        existingEvent.setIsAllDay(eventDto.getIsAllDay());
        existingEvent.setColor(eventDto.getColor());

        Event updatedEvent = eventRepository.update(existingEvent);
        return convertToDto(updatedEvent);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private EventDto convertToDto(Event event) {
        EventDto dto = new EventDto();
        dto.setId(event.getId());
        dto.setTitle(event.getTitle());
        dto.setDescription(event.getDescription());
        dto.setEventDate(event.getEventDate());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setLocation(event.getLocation());
        dto.setType(event.getType());
        dto.setPriority(event.getPriority());
        dto.setCreatedById(event.getCreatedById());
        dto.setIsAllDay(event.getIsAllDay());
        dto.setColor(event.getColor());

        // Set creator name
        if (event.getCreatedById() != null) {
            userRepository.findById(event.getCreatedById())
                    .ifPresent(creator -> dto.setCreatedByName(creator.getName()));
        }

        return dto;
    }

    private Event convertToEntity(EventDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setEventDate(dto.getEventDate());
        event.setStartTime(dto.getStartTime());
        event.setEndTime(dto.getEndTime());
        event.setLocation(dto.getLocation());
        event.setType(dto.getType());
        event.setPriority(dto.getPriority());
        event.setCreatedById(dto.getCreatedById());
        event.setIsAllDay(dto.getIsAllDay());
        event.setColor(dto.getColor());
        return event;
    }
}
