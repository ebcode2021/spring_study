package com.kh.spring.member.model.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;

@Repository //우리가 만든 리포지토리를 올려주는 역할
public class MemberRepository {

	@Autowired
	private SqlSessionTemplate session;
	
	public String selectPasswordByUserId(String userId) {
		return session.selectOne("com.kh.spring.mybatis.MybatisMapper.selectPasswordByUserId",userId);
	}
	
	
	
	
}
/*public interface MemberRepository {
	
	
	
	
	
	  @Select("select password from member where user_id = #{userId}") String
	  selectPasswordByUserId(@Param("userId") String userId);
	 
	
}*/
