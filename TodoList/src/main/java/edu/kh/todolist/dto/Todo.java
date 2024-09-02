package edu.kh.todolist.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
							// 직렬화 
	private int todoNo;
	private String title;
	private String detail;
	private String complete;
	private String regDate;
	
}
