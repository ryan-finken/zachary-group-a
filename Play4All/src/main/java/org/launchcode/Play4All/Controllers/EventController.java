package org.launchcode.Play4All.Controllers;


import org.launchcode.Play4All.data.EventRepository;
import org.launchcode.Play4All.data.VenueRepository;
import org.launchcode.Play4All.models.Event;
import org.launchcode.Play4All.models.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

   // @Autowired
   // private VenueRepository venueRepository;

    @GetMapping
    public String displayEvents(@RequestParam(required = false) Integer eventId, Model model) {

        if (eventId == null) {
            model.addAttribute("title", "All Events");
            model.addAttribute("event", eventRepository.findAll());
        } else {
            Optional<Event> result = eventRepository.findById(eventId);
            if (result.isEmpty()) {
                model.addAttribute("title", "Invalid Category ID: " + eventId);
            } else {
                Event event = result.get();
                model.addAttribute("title", "Event in Venue: " + event.getName());
                model.addAttribute("event", event.getDescription());
            }
        }

        return "event/index";
    }


    @GetMapping("create")
    public String displayCreateEventForm(Model model) {
        model.addAttribute("title", "Create Event");
        model.addAttribute(new Event());
       // model.addAttribute("categories", eventRepository.findAll());
       // model.addAttribute("name", eventRepository.)
        return "event/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model) {
        if(errors.hasErrors()) {
            model.addAttribute("title", "Create Event");
            return "event/create";
        }

        eventRepository.save(newEvent);
        return "redirect:";
    }
}
