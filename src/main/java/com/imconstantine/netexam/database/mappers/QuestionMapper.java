package com.imconstantine.netexam.database.mappers;

import com.imconstantine.netexam.model.Question;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface QuestionMapper {

    @Select("SELECT id, number, content, correct FROM question WHERE exam_id = #{examId}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "answerList", column = "id", javaType = List.class,
                    many = @Many(select = "com.imconstantine.netexam.database.mappers.AnswerMapper.getAnswersById",
                    fetchType = FetchType.EAGER))
    })
    List<Question> getQuestionsById(int examId);

    @Insert("<script>" +
            "INSERT INTO question ( exam_id, number, content, correct ) " +
            "VALUES" +
            "<foreach item='question' collection='list' separator=','> " +
            "( #{examId}, #{question.number}, #{question.content}, #{question.correct} ) " +
            "</foreach>" +
            "</script>")
    @Options(useGeneratedKeys = true)
    void batchInsert(@Param("list") List<Question> questionsList, @Param("examId") int examId);

    @Delete("DELETE FROM question WHERE exam_id = #{exam_id}")
    void deleteQuestions(int examId);

}
