package com.chang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    @GetMapping("/index")
    public String index() {
        System.out.println("热加载哈嘿呼嘻！");
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择文件上传");
            return "redirect:uploadStatus";
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("D://" + file.getOriginalFilename());
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
                    "您成功上传 '" + file.getOriginalFilename() + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
