package com.academicportal.repository;

import com.academicportal.entity.Event;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository {
    
    List<Event> findAll();
    
    Event findById(Long id);
    
    List<Event> findByCreatedById(Long createdById);
    
    List<Event> findByType(Event.EventType type);
    
    List<Event> findByEventDateBetween(LocalDate startDate, LocalDate endDate);
    
    List<Event> findByEventDate(LocalDate eventDate);
    
    List<Event> findEventsInRange(LocalDate startDate, LocalDate endDate);
    
    List<Event> findByDateOrderByTime(LocalDate date);
    
    List<Event> findByPriority(Event.Priority priority);
    
    List<Event> findAllOrderByPriorityAndDate();
    
    Event save(Event event);
    
    Event update(Event event);
    
    void deleteById(Long id);
}





