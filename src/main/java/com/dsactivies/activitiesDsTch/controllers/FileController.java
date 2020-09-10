package com.dsactivies.activitiesDsTch.controllers;

import com.dsactivies.activitiesDsTch.Services.FIleService;
import com.dsactivies.activitiesDsTch.models.*;
import com.dsactivies.activitiesDsTch.repository.CodeActivitiesRepository;
import com.dsactivies.activitiesDsTch.repository.FilesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class FileController {
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;
    @Autowired
    private CodeActivitiesRepository er;
    @Autowired
    private FilesRepository Fr;

    @Autowired
    FIleService fileService;

    @RequestMapping(value="/up/{actId}", method=RequestMethod.GET)
    public ModelAndView index(@PathVariable(value="actId") long actId) {
        Event events = er.findByActId(actId);
        ModelAndView mv = new ModelAndView("event/upload");
        mv.addObject("events",events);
        return mv;
    }

    @RequestMapping(value="/up/{actId}", method=RequestMethod.POST)
    public String uploadFile(FilesDoc files, @PathVariable("actId") long actId, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        long SizeByte = file.getSize();
        long SizeKB = (SizeByte / 1024);
        long Size = (SizeKB / 1024);
        long SizePrice = 15728640;
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Selecione um arquivo");
            return "redirect:{actId}";
        } else if(Size > SizePrice) {
            redirectAttributes.addFlashAttribute("message", "Seu arquivo é muito grande!!");
            return "redirect:{actId}";
        }
        else {
            FileCounts Counts = new FileCounts();
            Counts.setFileCount(er.CountFiles());
            Event event = er.findByActId(actId);
            files.setEvent(event);
            files.setNamId(file.getOriginalFilename() + actId + Counts.getFileCount());
            files.setName(file.getOriginalFilename());
            files.setSize(Size);
            String url =  fileService.uploadFile(file);
            files.setLocation(url);
            Fr.save(files);
            redirectAttributes.addFlashAttribute("message",
                    "Você conseguiu enviar! " + file.getOriginalFilename() + "!");
            return "redirect:/up/{actId}";
        }
    }

    @RequestMapping(value="/dw/{namId}", method=RequestMethod.GET)
    public HttpEntity<byte[]> download(@PathVariable(value="namId") String namId) throws IOException {
        FilesDoc files = Fr.findByNamId(namId);
        String Dir = files.getName();
        byte[] arquivo = Files.readAllBytes( Paths.get(files.getLocation()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment;filename=\""+ files.getName() +"\"");
        HttpEntity<byte[]> entity = new HttpEntity<byte[]>( arquivo, httpHeaders);
        return entity;
    }
}
