package com.dsactivies.activitiesDsTch.controllers;

import com.dsactivies.activitiesDsTch.models.*;
import com.dsactivies.activitiesDsTch.repository.CodeActivitiesRepository;
import com.dsactivies.activitiesDsTch.repository.FilesRepository;
import com.dsactivies.activitiesDsTch.repository.InfoAddRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class EventController {

    @Autowired
    private CodeActivitiesRepository er;
    @Autowired
    private InfoAddRepository cr;
    @Autowired
    private FilesRepository Fr;

    @RequestMapping(value="/registerEvent", method = RequestMethod.GET)
    public String form() {
        return "event/formEvent";
    }
    @RequestMapping(value="/registerEvent", method = RequestMethod.POST)
    public String form(@Valid Event evento, BindingResult result, RedirectAttributes attributes) {
        if(result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Encontramos um erro ao enviar, verifique os campos.");
            System.out.println(er.CountEvents());
            return "redirect:/registerEvent";
        } else {
            er.save(evento);
            attributes.addFlashAttribute("mensagem", "Adicionado com sucesso.");
            return "redirect:/registerEvent";
        }
    }
    @RequestMapping("/event")
    public ModelAndView listEvent(){
        Counts Count = new Counts();
        ModelAndView mv = new ModelAndView("index");
        Iterable<Event> events = er.findAll();
        Count.setEventCount(er.CountEvents());
        mv.addObject("Count",Count);
        mv.addObject("events",events);
        return mv;
    }
    @RequestMapping(value="/{actId}", method=RequestMethod.GET)
    public ModelAndView detailsEvent(@PathVariable("actId") long actId){
        Event event = er.findByActId(actId);
        ModelAndView mv = new ModelAndView("event/DetailsEvent");
        mv.addObject("event", event);

        Iterable<FilesDoc> files = Fr.findByEvent(event);
        mv.addObject("files", files);

        Iterable<InfoAdd> inf = cr.findByEvent(event);
        mv.addObject("inf", inf);
        return mv;
    }
    @RequestMapping(value="/{actId}", method=RequestMethod.POST)
    public String detailsInf(@PathVariable("actId") long actId, @Valid InfoAdd inf, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Encontramos um erro ao enviar, verifique os campos.");
            return "redirect:/{actId}";
        } else {
            InfCount Counts = new InfCount();
            Counts.setInfCount(er.CountInf());
            Event event = er.findByActId(actId);
            inf.setEvent(event);
            inf.setAuthorId(inf.getAuthor() + actId + Counts.getInfCount());
            cr.save(inf);
            attributes.addFlashAttribute("mensagem", "Adicionado com sucesso.");
            return "redirect:/{actId}";
        }
    }

    @RequestMapping("/delect")
    public String delectEvent(long actId){
        Event event = er.findByActId(actId);
        Iterable<FilesDoc> files = Fr.findByEvent(event);
        Fr.deleteAll(files);
        Iterable<InfoAdd> inf = cr.findByEvent(event);
        cr.deleteAll(inf);
        er.delete(event);
        return "redirect:/event";
    }
    @RequestMapping("delectInf")
    public String delectInf(String authorId){
        InfoAdd inf = cr.findByAuthorId(authorId);
        Event event = inf.getEvent();
        long LongactId = event.getActId();
        String actId = "" + LongactId;
        cr.delete(inf);
        return "redirect:/" + actId;
    }
}