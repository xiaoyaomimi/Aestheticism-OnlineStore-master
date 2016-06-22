package pl.surecase.eu;



import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;


public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        //bean存放的位置 // 1: 数据库版本号
        // com.xxx.bean:自动生成的Bean对象会放到/java-gen/com/xxx/bean中
        Schema schema = new Schema(3, "com.caidongdong.aestheticism.entity");
       //Dao存放的位置
        schema.setDefaultJavaPackageDao("com.caidongdong.aestheticism.dao");
        initUser(schema);
        initTable(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java");
    }

    /**
     * 用户表
     * @param schema
     */
    public static void initUser(Schema schema) {
        Entity userBean = schema.addEntity("User");
        userBean.setTableName("User");
        userBean.addIdProperty().primaryKey().index().autoincrement();//主键，索引
        userBean.addStringProperty("User_Name").unique().notNull();
        userBean.addStringProperty("PassWord").notNull();
        userBean.addStringProperty("Gender").notNull();
        userBean.addIntProperty("Phone_Num").notNull();
        userBean.addStringProperty("True_name");
        userBean.addStringProperty("ID_Card");
        userBean.addStringProperty("Create_Time");
        userBean.addStringProperty("Update_Time");
        userBean.addStringProperty("Last_Login_Time");
        userBean.addBooleanProperty("Enable");
    }

    public static void initTable(Schema schema) {
        //商家表
        Entity seller = schema.addEntity("Seller");
        seller.setTableName("Seller");
        seller.addIdProperty().primaryKey().index().autoincrement();
        seller.addStringProperty("Seller_Name").unique().notNull();
        seller.addStringProperty("Pass_Word").notNull();
        seller.addStringProperty("Gender").notNull();
        seller.addIntProperty("Phone_Num");
        seller.addStringProperty("True_Name");
        seller.addStringProperty("ID_Card");
        seller.addStringProperty("Create_Time");
        seller.addStringProperty("Update_Time");
        seller.addStringProperty("Last_Login_Time");
        seller.addBooleanProperty("Enable");

        //店铺表
        Entity store = schema.addEntity("Store");
        store.setTableName("Store");
        store.addIdProperty().primaryKey().index().autoincrement();
        store.addStringProperty("Store_Name").unique().notNull();
        store.addStringProperty("Store_Code").unique().notNull();
        store.addIntProperty("Store_Grade").unique().notNull();
        store.addStringProperty("Store_Ico").unique().notNull();
        store.addStringProperty("Store_Description");
        store.addIntProperty("Store_Fans_Num");
        store.addIntProperty("Store_Item_Num");
        store.addIntProperty("Seller_Id").notNull();
        //将店铺和商家关联起来，一个商家只允许一个店铺
        Property sellerId = store.getPkProperty();
        store.addToOne(seller,sellerId);

    }

//    public static void initStore(Schema schema) {
//        Entity store = schema.addEntity("Store");
//        store.setTableName("Store");
//        store.addIdProperty().primaryKey().index().autoincrement();
//        store.addStringProperty("Store_Name").unique().notNull();
//        store.addStringProperty("Store_Code").unique().notNull();
//        store.addIntProperty("Store_Grade").unique().notNull();
//        store.addStringProperty("Store_Ico").unique().notNull();
//        store.addStringProperty("Store_Description");
//        store.addIntProperty("Store_Fans_Num");
//        store.addIntProperty("Store_Item_Num");
//        store.addIntProperty("Seller_Id").notNull();
//    }
}
