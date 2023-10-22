package zk.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("全局返回结果")
public class Result<T> implements Serializable {
    @ApiModelProperty("响应码")
    private Integer code;//响应码，1 代表成功; 0 代表失败
    @ApiModelProperty("响应信息")
    private String msg;  //响应信息 描述字符串
    @ApiModelProperty("返回的数据")
    private T data; //返回的数据

    //增删改 成功响应
    public static <T> Result<T> success(){
        return new Result(1,"success",null);
    }
    //查询 成功响应
    public static <T >Result <T> success(T data){
        return new Result(1,"success",data);
    }
    //失败响应
    public static<T> Result<T> error(String msg){
        return new Result(0,msg,null);
    }
}
