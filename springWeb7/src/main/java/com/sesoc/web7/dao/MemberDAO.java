package com.sesoc.web7.dao;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sesoc.web7.vo.MemberVO;


@Repository
public class MemberDAO {
	@Autowired
	private SqlSession session;
	
	public void signup(MemberVO vo) {
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		try {
			mapper.signup(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public MemberVO login(MemberVO vo) {
//		MemberMapper mapper = session.getMapper(MemberMapper.class);
//		MemberVO result = new MemberVO();
//		try {
//			result = mapper.login(vo.getUserid());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	public int login(MemberVO vo, HttpSession httpSession) {
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		int result = 0;
		try {
			result = mapper.login(vo);
			if(result != 0) {
				httpSession.setAttribute("userid", vo.getUserid());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public void logout(HttpSession httpSession) {
		httpSession.removeAttribute("userid");
	}
}
