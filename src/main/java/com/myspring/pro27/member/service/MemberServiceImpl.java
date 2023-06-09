package com.myspring.pro27.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro27.member.dao.MemberDAO;
import com.myspring.pro27.member.vo.MemberVO;

@Service(value = "memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService{
	
//	MemberDAO瑜� 二쇱엯(inject)
	@Autowired
	private MemberDAO memberDAO;
	



	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		membersList = memberDAO.selectAllMembers();
		return membersList;
	}
	
	@Override
	public int addMember(MemberVO memberVO)throws DataAccessException{
		return memberDAO.insertMember(memberVO);
	}


	/*
	 * @Override public int modMember(MemberVO memberVO) throws DataAccessException
	 * { return memberDAO.modMember(memberVO); }
	 */
	
	@Override
	public int removeMember(String id) throws DataAccessException {
		return memberDAO.removeMember(id);
	}

	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
	
		return memberDAO.loginById(memberVO);
	}
	
	


	
	
	
}
