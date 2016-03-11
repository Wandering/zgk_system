package cn.thinkjoy.jx.k12system.util;

/**
 * Created by wangyongqiang on 15/9/9.
 */
public class CodeFactoryUtil {

    /**
     * 产品、代理公司、部门、岗位、人员、账户等表名信息
     */
    public static String PRODUCT_TABLE="k12system_product";
    public static String COMPANY_TABLE="k12system_company";
    public static String DEPARTMENT_TABLE="k12system_department";
    public static String POSITION_TABLE="k12system_post";
    public static String USERINFO_TABLE="k12system_user_info";
    public static String ACCOUNT_TABLE="k12system_user_account";

    /**
     * 产品、代理公司、部门、岗位、人员、账户等Code字段信息
     */
    public static String PRODUCT_CODE="productCode";
    public static String COMPANY_CODE="companyCode";
    public static String DEPARTMENT_CODE="departmentCode";
    public static String POSITION_CODE="postCode";
    public static String USERINFO_CODE="userCode";
    public static String ACCOUNT_CODE="accountCode";

    /**
     * 默认排序字段 按新增修改时间
     */

    public static String ORDER_BY_FIELD = "createDate";

    public static String ORDER_BY_SEQSORT = "seqSort";


    public static long PRUDUCT_RULE = 10;

    public static long COMPANY_RULE = 1000;

    public static long DEPARTMENT_RULE = 1000;

    public static long POSITION_RULE = 100;

    public static long USER_RULE = 1000;

    public static long ACCOUNT_RULE=1000;

    public static long OTHER_RULE=10;


    /**
     * 初始化产品Code
     * @return  10-99
     */
    public static long getInitProduct(){
        return PRUDUCT_RULE;
    }

    /**
     * 初始化代理公司Code
     * @param productCode
     * @return 10001-10999
     */
    public static long getInitCompany(long productCode){
        return productCode * COMPANY_RULE+1;
    }

    /**
     * 初始化部门Code
     * @param companyCode
     * @return 10001001-10001999
     */
    public static long getInitDepartment(long companyCode){
        return companyCode * DEPARTMENT_RULE+1;
    }

    /**
     * 初始化岗位Code
     * @param departmentCode
     * @return 10001001
     */
    public static long getInitPosition(long departmentCode){
        return departmentCode* POSITION_RULE+1;
    }

    /**
     * 初始化人员Code
     * @param departmentCode
     * @return
     */
    public static long getInitUserInfo(long departmentCode){
        return departmentCode/DEPARTMENT_RULE * USER_RULE+1;
    }



    /**
     * 根据部门Code获取人员最小范围Code
     * @param departmentCode
     * @return
     */
    public static long getMinUserInfoCode(long departmentCode){
        return departmentCode/DEPARTMENT_RULE * USER_RULE;
    }

    /**
     * 根据部门Code获取人员最大范围Code
     * @param departmentCode
     * @return
     */
    public static long getMaxUserInfoCode(long departmentCode){
        return (departmentCode/DEPARTMENT_RULE+1) * USER_RULE;
    }


    /**
     * 初始化产品管理员账户
     * @param productCode
     * @return
     */
    public static long getInitProductAccount(long productCode){
        return  productCode*ACCOUNT_RULE+1;
    }

    /**
     * 初始化代理公司管理员账户
     * @param companyCode
     * @return
     */
    public static long getInitCompanyAccount(long companyCode){
        return  companyCode*ACCOUNT_RULE+1;
    }

    /**
     * 初始化员工管理员账户
     * @param departmentCode
     * @return
     */
    public static long getInitAccount(long departmentCode){
        return  departmentCode/DEPARTMENT_RULE*ACCOUNT_RULE+1;
    }


    public static long getInitOtherCode(){
        return OTHER_RULE;
    }
}
