package kr.co.boot.ajax;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ajax_mapper {
	int api_insert(Map<String, String> map);
}
