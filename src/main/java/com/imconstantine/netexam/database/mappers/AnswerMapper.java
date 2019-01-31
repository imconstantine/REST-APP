package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.Answer;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AnswerMapper {

    @Select("SELECT id, content AS body FROM answer WHERE question_id = #{id}")
    List<Answer> getAnswersById(int id);

    @Insert("<script>" +
            "INSERT INTO answer ( question_id, content ) " +
            "VALUES" +
            "<foreach item='answer' collection='list' separator=','> " +
            "( #{questionId}, #{answer.body} ) " +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true)
    void batchInsert(@Param("list") List<Answer> answerList, @Param("questionId") int questionId);

}
