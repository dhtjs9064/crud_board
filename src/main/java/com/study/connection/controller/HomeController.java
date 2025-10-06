package com.study.connection.controller;// 새로운 파일: HomeController.java

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 클래스 레벨에 매핑 경로 없음
public class HomeController {

    // 루트 경로 (http://localhost:8080/) 요청만 전담하여 처리
    @GetMapping("/")
    public String home() {
        // 이미 완성된 게시판 목록 페이지로 리다이렉트
        return "redirect:/boards/free/list";
    }
}