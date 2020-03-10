package com.sesoc.web7.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sesoc.web7.dao.MemberDAO;
import com.sesoc.web7.vo.MemberVO;

@Controller
public class MemberController {
	
	@Autowired
	private MemberDAO dao;
	
	@RequestMapping(value = "member/signupForm", method = RequestMethod.GET)
	public String signupForm() {
		return "member/signupForm";
	}
	@RequestMapping(value = "member/signup", method = RequestMethod.POST)
	public String signup(MemberVO vo) {
		// DB 에 vo 값을 저장하는 로직을 수행하는 메서드 호출
		dao.signup(vo);
		return "main";
	}
	
	@RequestMapping(value = "member/loginForm", method = RequestMethod.GET)
	public String loginForm() {
		return "member/loginForm";
	}
	 
//	@RequestMapping(value = "member/login", method = RequestMethod.POST)
//	public String login(MemberVO vo, HttpSession session) {
//		MemberVO result = dao.login(vo);
//		if(result != null) {
//			session.setAttribute("userid", result.getUserid());
//			return "main";
//		} else {
//			return "member/loginForm";
//		}
//	}
	@RequestMapping(value = "member/login", method = RequestMethod.POST)
	public String login(MemberVO vo, HttpSession httpSession) {
		int result = dao.login(vo, httpSession);
		if(result == 0) return "member/loginForm";
		return "main";
	}
	@RequestMapping(value = "member/logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		dao.logout(httpSession);
		return "main";
	}
}
