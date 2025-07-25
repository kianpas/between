package com.wb.between.faq.dto.response.user;

import com.wb.between.faq.domain.FaQ;
import lombok.Getter;


@Getter
public class FaQListViewResponse {
    private final int qNo;
   private final String question;
   private final String answer;

   public FaQListViewResponse(FaQ faq){
       this.qNo = faq.getQNo();
       this.question = faq.getQuestion();
       this.answer = faq.getAnswer();
   }


}
