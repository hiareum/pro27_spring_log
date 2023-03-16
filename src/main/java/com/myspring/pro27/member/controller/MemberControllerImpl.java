package com.myspring.pro27.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.dao.MemberDAO;
import com.myspring.pro27.member.dao.MemberDAOImpl;
import com.myspring.pro27.member.service.MemberService;
import com.myspring.pro27.member.vo.MemberVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(value = "memberController")
public class MemberControllerImpl  implements  MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);

	//@Autowired�쑝濡� �옄�룞�쑝濡� 二쇱엯 �깮�꽦�옄,�븘�뱶,�뀑�꽣硫붿꽌�뱶,硫붿꽌�뱶�꽕�젙�뿉 �쓽議댁꽦 二쇱엯
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberVO memberVO;
	
	
	

//한글로바뀐주석 깃허브에 올린내용으로 바꾸기
//	@Autowired�쓣 �벐湲� �쟾�뿉�뒗 set�빐以ъ뼱�빞 �뻽�떎
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}


	@Override
	@RequestMapping(value = "/member/listMembers.do" , method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		logger.info("viewName:" +viewName);
		logger.debug("viewName:" +viewName);
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
		
	}
	
	@RequestMapping(value = "/member/*Form.do" , method = RequestMethod.GET)
	public ModelAndView memberForm(@RequestParam(value= "result", required=false) String result,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		logger.debug("viewName:" +viewName);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}
	
	
	@Override
	@RequestMapping(value = "/member/removeMember.do" , method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id")String id,HttpServletRequest request, HttpServletResponse response) throws Exception{
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
		
	}
	
	@Override
	@RequestMapping(value = "/member/addMember.do" , method = RequestMethod.POST)
	//�씤�꽣�럹�씠�뒪�뿉�룄 @ModelAttribute("member")MemberVO member,�엯�젰�빐以섏빞�븿
	public ModelAndView addMember(@ModelAttribute("member")MemberVO member,HttpServletRequest request, HttpServletResponse response) throws Exception {
		int result = 0;
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
		
	}
	


/*	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
		String viewName = getViewName(request);
		
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email=request.getParameter("email");

		MemberVO memberVO =new MemberVO();
		memberVO.setId(id);
		memberVO.setPwd(pwd);
		memberVO.setName(name);
		memberVO.setEmail(email);
		request.setAttribute("MemberVO", memberVO);
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		return mav;
		
	}  */
	

	
//	
		

//	public ModelAndView modMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("utf-8");	
//		MemberVO memberVO =new MemberVO();
//		//�슂泥��뱾�뼱�삩 �궡�슜�쓣 紐⑤몢 諛붿씤�뱶
//		bind(request, memberVO);
//		int result = 0;
//		result = memberService.modMember(memberVO);
//		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
//		return mav;
//		
//	}
	
	
	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String fileName = uri.substring(begin, end);
		System.out.println(fileName);
		if (fileName.indexOf(".") != -1) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		if (fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/",1), fileName.length());
		}
		return fileName;
	}

@Override
@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
public ModelAndView login(@ModelAttribute("member") MemberVO member,
			              RedirectAttributes rAttr,
	                       HttpServletRequest request, HttpServletResponse response) throws Exception {
ModelAndView mav = new ModelAndView();
memberVO = memberService.login(member);
if(memberVO != null) {
	    HttpSession session = request.getSession();
	    session.setAttribute("member", memberVO);
	    session.setAttribute("isLogOn", true);
	    mav.setViewName("redirect:/member/listMembers.do");
}else {
	    rAttr.addAttribute("result","loginFailed");
	    mav.setViewName("redirect:/member/loginForm.do");
}
return mav;
}

@Override
@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
	HttpSession session = request.getSession();
	session.removeAttribute("member");
	session.removeAttribute("isLogOn");
	ModelAndView mav = new ModelAndView();
	mav.setViewName("redirect:/member/listMembers.do");
	return mav;
}



	


	

}
