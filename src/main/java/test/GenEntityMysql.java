package test;

/**
 * @Author : kooing
 * @Date : 2017/10/15 - 19:53
 * @Desription :
 * @Modified by :
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;

public class GenEntityMysql {

    private String packageOutPath = "com.user.entity";//指定实体生成所在包的路径
    private String authorName = "封狼居胥";//作者名字
    private String tablename = "user";//表名
    private String[] colnames; // 列名数组
    private String colTypes; //列名类型数组
    private int[] colSizes; //列名大小数组
    private boolean f_util = false; // 是否需要导入包java.util.*
    private boolean f_sql = false; // 是否需要导入包java.sql.*

    //数据库连接
    private static final String URL = "jdbc:mysql://localhost:3306/db_kooing_saas";
    private static final String NAME = "root";
    private static final String PASS = "";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    /*
     * 构造函数
     */
    public GenEntityMysql() {
        //创建连接
        Connection con;
        //查要生成实体类的表
        String sql = "select * from " + tablename;
        PreparedStatement pStemt = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            con = DriverManager.getConnection(URL, NAME, PASS);
            pStemt = con.prepareStatement(sql);
            ResultSetMetaData rsmd = pStemt.getMetaData();
            int size = rsmd.getColumnCount();    //统计列
            colnames = new String[size];
            colTypes = new String();
            colSizes = new int[size];
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes = rsmd.getColumnTypeName(i + 1);

                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }

            String content = parse(colnames, colTypes, colSizes);

            try {
                File directory = new File("");
                //System.out.println("绝对路径："+directory.getAbsolutePath());
                //System.out.println("相对路径："+directory.getCanonicalPath());
                String path = this.getClass().getResource("").getPath();

                System.out.println(path);
                System.out.println("src/?/" + path.substring(path.lastIndexOf("/com/", path.length())));
//				String outputPath = directory.getAbsolutePath()+ "/src/"+path.substring(path.lastIndexOf("/com/", path.length()), path.length()) + initcap(tablename) + ".java";
                String outputPath = directory.getAbsolutePath() + "/src/" + this.packageOutPath.replace(".", "/") + "/" + initcap(tablename) + ".java";
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//			try {
//				con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
    }

    /**
     * 功能：生成实体类主体代码
     *
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();
        //包名

        sb.append("package " + this.packageOutPath + ";\r\n");
        sb.append("\r\n");
        //注释部分
        sb.append("   /**\r\n");
        sb.append("    * " + tablename + " 实体类\r\n");
        sb.append("    * " + new Date() + "\r\n");
        sb.append("    * " + "@author:" + this.authorName + "\r\n");
        sb.append("    */ \r\n");
        //导入包
        sb.append("import java.util.Date;\r\n");
        //实体部分
        sb.append("\r\n\r\npublic class " + initcap(tablename) + "{\r\n");
        processAllAttrs(sb);//属性
        sb.append("}\r\n");

        //System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     * 功能：生成所有属性
     *
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {

        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + " String " + colnames[i] + ";\r\n");
        }

    }


    /**
     * 功能：将输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }


    /**
     * 出口
     * TODO
     *
     * @param args
     */
    public static void main(String[] args) {

        new GenEntityMysql();

    }

}