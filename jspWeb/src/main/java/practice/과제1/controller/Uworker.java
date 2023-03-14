package practice.과제1.controller;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import practice.과제1.model.dao.CompanyDao;
import practice.과제1.model.dto.CompanyDto;


@WebServlet("/Uworker")
public class Uworker extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public Uworker() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//저장공간
				String uploadpath = request.getSession().getServletContext().getRealPath("/practice/과제1/img");
				System.out.println( uploadpath );
				
				// * 업로드 [ 유저파일 --> 서버폴더내 이동 ]
				MultipartRequest multi = new MultipartRequest(
						request,  						// 1. 요청방식 
						uploadpath , 					// 2. 첨부파일 가져와서 저장할 서버내 폴더 
						1024*1024 * 10 ,				// 3. 첨부파일 허용 범위 용량[ 바이트단위 ] 10MB
						"UTF-8" ,						// 4. 첨부파일 한글 인코딩 
						new DefaultFileRenamePolicy() 	// 5. 동일한 첨부파일명이 존재했을때 뒤에 숫자 붙여서 식별
						);
				
				// 그외 매개변수 요청 [ request --> multi / form 하위태그내 input 태그의 name 식별자  ]
				int wno = Integer.parseInt( multi.getParameter("wno"));
				String name = multi.getParameter("name");	
				String grade = multi.getParameter("grade"); 
				String worker = multi.getParameter("worker");
				String department = multi.getParameter("department");
				String join = multi.getParameter("join");
				String retire = multi.getParameter("retire");
				String reason = multi.getParameter("reason");
				
				String picture = multi.getFilesystemName("picture");	// 첨부파일된 파일명 호출[  .getFilesystemName ]

				if(picture == null) {
					picture = CompanyDao.getInstance().Eprint(wno).getPicture();
				}
				
				CompanyDto dto = new CompanyDto(wno,name,grade,worker,department,join,picture,retire,reason);
				System.out.println("tostring "+dto.toString());
				
				boolean result =  CompanyDao.getInstance().edit(dto);
				System.out.println("DB결과: "+result);
				response.getWriter().print(result);
	
	}

}
