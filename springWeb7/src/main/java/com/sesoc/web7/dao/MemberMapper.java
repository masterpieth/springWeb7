package com.sesoc.web7.dao;

import com.sesoc.web7.vo.MemberVO;

public interface MemberMapper {

	public void signup(MemberVO vo);
//	public MemberVO login(String userid);
	public int login(MemberVO vo);
}
