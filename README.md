# dbToPojoUtil
一个自己用的数据库模型类 转 实体工具

## 作用：完成数据库类转换成都是String类型的实体类
## 操作：在资源文件夹下创建properties/dbToPojoUtil.properties文件
### 数据库我写死了在代码，需要的自己修改下链接数据库的url
######必填部分 #########<br>
#包名<br>
packageOutPath=com.kooing.saas.params.admin.service.param.response<br>
#表名<br>
tableName=tb_activity_reg<br>
#导入的包,如果有多个包用(,)隔开,包名其实不必写全，可在ide上自动补全<br>
importPackage=com.kooing.framework.param.common.request.BaseReq,lombok.Getter,lombok.Setter<br>
#源文件目录，idea下默认/src/main/java/<br>
sourceRoot=/src/main/java/<br><br>
<br>
####可选部分    ############<br>
#注解,如果有多个注解用(,)隔开<br>
annotation=@Setter,@Getter<br>
#extend继承<br>
extendsObject=BaseReq<br>
#implements实现接口，暂时实现为一个接口<br>
implementsObject=Serializable<br>
#作者<br>
authorName=kooing1<br>
#类的后缀<br>
classSuffix=bb<br>
#类的前缀(记得大写<br>
classPrefix=Aa<br>
#注入数据库字段注释(1),默认不开<br>
dbComment=1<br>
1
2
3
4
5