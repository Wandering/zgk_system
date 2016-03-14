package cn.thinkjoy.zgk.zgksystem.controller;

import cn.thinkjoy.common.exception.BizException;
import cn.thinkjoy.zgk.zgksystem.common.ERRORCODE;
import cn.thinkjoy.zgk.zgksystem.common.HttpUtil;
import cn.thinkjoy.zgk.zgksystem.common.Page;
import cn.thinkjoy.zgk.zgksystem.common.TreeBean;
import cn.thinkjoy.zgk.zgksystem.domain.*;
import cn.thinkjoy.zgk.zgksystem.pojo.ProductPojo;
import cn.thinkjoy.zgk.zgksystem.pojo.UserPojo;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserAccountService;
import cn.thinkjoy.zgk.zgksystem.service.account.IUserInfoService;
import cn.thinkjoy.zgk.zgksystem.service.company.ICompanyService;
import cn.thinkjoy.zgk.zgksystem.service.product.IEXProductService;
import cn.thinkjoy.zgk.zgksystem.service.product.IProductService;
import cn.thinkjoy.zgk.zgksystem.service.code.IEXCodeService;
import cn.thinkjoy.zgk.zgksystem.service.system.IK12systemPostService;
import cn.thinkjoy.zgk.zgksystem.util.CodeFactoryUtil;
import cn.thinkjoy.zgk.zgksystem.util.Constants;
import cn.thinkjoy.zgk.zgksystem.util.IdentityUtil;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.jlusoft.microschool.core.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wangyongqiang on 15/9/1
 *
 *
 *
 */
@Controller
@RequestMapping("/system/product")
public class ProductController {

    private static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private IProductService productService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IUserAccountService userAccountService;

    @Autowired
    private IEXProductService iexproductService;

    @Autowired
    private IK12systemPostService k12systemPostService;

    @Autowired
    private IEXCodeService excodeService;

    /**
     * 新增和修改人员信息
     * @return String
     *
     */
    @ResponseBody
    @RequestMapping(value = "addOrEditProduct", method = RequestMethod.POST)
    public String addOrEditProduct(HttpServletRequest request){
        UserPojo userPojo = (UserPojo)HttpUtil.getSession(request,"user");
        String productJson = request.getParameter("productJson");
        if(StringUtils.isBlank(productJson)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
        ProductPojo product=null;
        try{
             product =  JsonMapper.buildNormalMapper().fromJson(productJson, ProductPojo.class);
        }catch (Exception e){
            LOGGER.error(ERRORCODE.JSONCONVERT_ERROR.getMessage());
            throw new BizException(ERRORCODE.JSONCONVERT_ERROR.getCode(),ERRORCODE.JSONCONVERT_ERROR.getMessage());
        }
        Product p=new Product();
        p.setId(product.getProductId());
        p.setProductName(product.getProductName());
        p.setAddressJson(product.getAddressJson());
        p.setDescription(product.getDescription());
        p.setProductIndexPage(product.getProductIndexPage());
        p.setProductLogo(product.getProductLogo());
        p.setSeqSort(product.getSeqSort());
        if(product.getProductId()==null || product.getProductId()==0 ){
            //新建产品、创建产品管理员信息以及账户信息
            p.setCreateDate(System.currentTimeMillis());
            p.setCreator(userPojo.getAccountCode());
            Long maxProductCode=excodeService.selectMaxCode(CodeFactoryUtil.PRODUCT_CODE,CodeFactoryUtil.PRODUCT_TABLE);
            if(maxProductCode==null||maxProductCode==0){
                maxProductCode= CodeFactoryUtil.getInitProduct();//初始化默认产品Code
            }else{
                ++maxProductCode;
            }
            p.setProductCode(maxProductCode);

            //创建产品同时创建账户的基本信息
            //特殊处理产品管理员的账户和用户Code
            Long  maxAccountCode= CodeFactoryUtil.getInitProductAccount(maxProductCode);
            UserInfo userInfo=new UserInfo();
            userInfo.setUserName(product.getUserName());
            userInfo.setDepartmentCode((long)IdentityUtil.PRODUCT_MANAGER_DEPARTMENT);
            userInfo.setPostCode((long) IdentityUtil.PRODUCT_MANAGER_POST);
            userInfo.setEmail(product.getEmail());
            userInfo.setPhone(product.getPhone());
            userInfo.setUserCode(maxAccountCode);
            userInfoService.updateOrSave(userInfo,null);

            //创建产品同时创建产品管理员账户
            UserAccount userAccount=new UserAccount();
            userAccount.setLoginNumber(product.getLoginNumber());
            userAccount.setPassword(product.getPassword());
            userAccount.setUserType(Constants.USER_TYPE_NORMAL);
            userAccount.setIdentityCode((long) IdentityUtil.PRODUCT_MANAGER_POST);
            userAccount.setUserCode(maxAccountCode);
            userAccount.setAccountCode(maxAccountCode);
            userAccountService.updateOrSave(userAccount,null);

            //创建产品管理员默认登录K12系统的关联关系权限
            Map<String,Object> k12systemPostMap=new HashMap<>();
            k12systemPostMap.put("postCode", (long) IdentityUtil.PRODUCT_MANAGER_POST);
            k12systemPostMap.put("systemCode", Constants.SYSTEM_CODE);
            List<K12systemPost> k12systemPosts=k12systemPostService.queryList(k12systemPostMap,null,null);
            if(k12systemPosts==null ||  k12systemPosts.size()==0){
                K12systemPost k12systemPost=new K12systemPost();
                k12systemPost.setPostCode((long) IdentityUtil.PRODUCT_MANAGER_POST);
                k12systemPost.setSystemCode(Constants.SYSTEM_CODE);
                k12systemPost.setCreateDate(System.currentTimeMillis());
                k12systemPost.setCreator(userPojo.getAccountCode());
                k12systemPostService.insert(k12systemPost);
            }
        }else{
            //修改产品
            p.setLastModDate(System.currentTimeMillis());
            p.setLastModifier(userPojo.getAccountCode());
        }
        productService.updateOrSave(p, p.getId());
        return "ok";

    }


    /**
     * 通过产品名称检查是否已存在的产品信息
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value="checkProduct",method=RequestMethod.POST)
    public String checkProduct(HttpServletRequest request){
        String productName=request.getParameter("productName");
        if(StringUtils.isBlank(productName)){
            throw new BizException(ERRORCODE.PARAM_ISNULL.getCode(),ERRORCODE.PARAM_ISNULL.getMessage());
        }
       List<Product> productList= productService.findList("productName", productName);
        if(productList!=null && productList.size()>0){
            throw  new BizException(ERRORCODE.ALREADY_EXIST_ERROR.getCode(),ERRORCODE.ALREADY_EXIST_ERROR.getMessage());
        }else{
            return  "ok";
        }
    }


    /**
     * 删除产品
     * @param request request
     * @return String
     */
    @ResponseBody
    @RequestMapping(value = "delProduct",method = RequestMethod.GET)
    public String delProduct(HttpServletRequest request){
        String productId = request.getParameter("id");
        if(StringUtils.isBlank(productId)){
            throw  new BizException(ERRORCODE.PARAM_ISNULL.getCode(), ERRORCODE.PARAM_ISNULL.getMessage());
        }
        Product p = (Product)productService.findOne("id", productId);
        List<Company> companyList= companyService.findList("productCode",p.getProductCode());//根据产品Code查询代理公司
        if(companyList!=null && companyList.size()>0){//删除产品前,查询是否有代理公司,如果有,请联系代理公司管理员先删除代理公司的信息。
            throw  new BizException(ERRORCODE.NOT_DELETEED.getCode(), ERRORCODE.NOT_DELETEED.getMessage());
        }
        p.setStatus(Constants.DELETEED_STATUS);

        //删除产品的同时级联删除相对应的产品管理员账户和账户信息
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        dataMap.put("deparmentCode",IdentityUtil.PRODUCT_MANAGER_DEPARTMENT);
        dataMap.put("postCode",IdentityUtil.PRODUCT_MANAGER_POST);
        dataMap.put("userCode", CodeFactoryUtil.getInitProductAccount(p.getProductCode()));
        UserInfo userInfo= (UserInfo) userInfoService.queryOne(dataMap);
        if(userInfo!=null){
            userInfo.setStatus(Constants.DELETEED_STATUS);
            userInfoService.update(userInfo);
        }


        //删除账户
        dataMap.put("accountCode", CodeFactoryUtil.getInitProductAccount(p.getProductCode()));
        UserAccount account=(UserAccount)userAccountService.queryOne(dataMap);
        if (account!=null) {
            account.setStatus(Constants.DELETEED_STATUS);
            userAccountService.update(account);
        }

        //删除产品管理员岗位与系统的关联关系表信息
        Map<String,Object> k12systemPostMap=new HashMap<>();
        dataMap.put("status",Constants.NORMAL_STATUS);
        dataMap.put("systemCode",Constants.SYSTEM_CODE);
        dataMap.put("postCode",IdentityUtil.PRODUCT_MANAGER_POST);
        K12systemPost k12systemPost=(K12systemPost)k12systemPostService.queryOne(k12systemPostMap);
        if(k12systemPost!=null){
            k12systemPost.setStatus(Constants.DELETEED_STATUS);
            k12systemPostService.update(k12systemPost);
        }
        productService.update(p);
        return "ok";
    }

    /**
     * 查询产品
     * @param request request
     * @return Page<T>
     */
    @ResponseBody
    @RequestMapping(value = "queryProduct",method = RequestMethod.GET)
    public Page<ProductPojo> queryProduct(HttpServletRequest request) {
        int currentPageNo = Integer.parseInt(HttpUtil.getParameter(request, "currentPageNo", "1"));
        int pageSize = Integer.parseInt(HttpUtil.getParameter(request, "pageSize", "10"));
       return iexproductService.queryProduct(currentPageNo,pageSize);

    }
    /**
     * 获取单个产品
     * @param request request
     * @return T
     */
    @ResponseBody
    @RequestMapping(value = "getProduct",method = RequestMethod.GET)
    public Product getProduct(HttpServletRequest request){
        String productId = request.getParameter("id");
       return  iexproductService.getProduct(productId);
    }

    /**
     * 获取产品Code和产品名称
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryComboxProduct",method = RequestMethod.GET)
    public Map<String,String> queryComboxProduct(){
        return iexproductService.queryComboxProduct();
    }
    /**
     * 获取产品Code和产品名称树形结构
     * @return Map
     */
    @ResponseBody
    @RequestMapping(value = "queryTreeProduct",method = RequestMethod.GET)
    public List<TreeBean> queryTreeProduct(HttpServletRequest request){
        UserPojo userPojo=(UserPojo)HttpUtil.getSession(request,"user");
        return iexproductService.queryTreeProduct(userPojo);
    }

}
