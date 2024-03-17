package com.storage_app.service;

import java.util.Optional;

import com.storage_app.entity.Quiz;

public interface QuizService {
	//全件取得
	Iterable<Quiz> selectAll();
	
	//ID指定で1件取得
	Optional<Quiz> selectOneById(Integer id);
	
	//ランダムに1件取得
	Optional<Quiz> selectOneRandomQuiz();	
	
	//正誤判定
	Boolean checkQuiz(Integer id, Boolean myAnswer);
	
	//登録
	void insertQuiz(Quiz quiz);
	
	//更新
	void updateQuiz(Quiz quiz);
	
	//削除
	void deleteQuizById(Integer id);
	
}
