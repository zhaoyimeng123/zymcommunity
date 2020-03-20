package com.zym.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2020/3/16-20:02
 */
@Data
public class ResultDTO {
    private Integer code;
    private String message;
    private List<CommentWithCommentNameAndAvatorAndCommentDTO> comments;

    public static ResultDTO errorOf(Integer code,String message){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO okOf(){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        return resultDTO;
    }

    public static ResultDTO okOfSubComment(List<CommentWithCommentNameAndAvatorAndCommentDTO> comments){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(200);
        resultDTO.setMessage("请求成功");
        resultDTO.setComments(comments);
        return resultDTO;
    }

    public ResultDTO(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public ResultDTO() {
    }
}
