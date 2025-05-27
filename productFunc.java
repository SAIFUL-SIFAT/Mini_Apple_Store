import java.sql.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.ZoneId;

public class productFunc
{
    public static ResultSet getProducts(String category)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result = null;
        String query = "SELECT * FROM products WHERE category = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category);
            result = preparedStatement.executeQuery();

        } catch (SQLException e) {e.printStackTrace();}

        return result;
    }


    public static int getProductCount(String category)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result = null;
        String query = "SELECT COUNT(*) FROM products WHERE category = ?";
        int count = 0;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category);
            result = preparedStatement.executeQuery();
            count = result.getInt(1);

            connection.close();
            result.close();

        } catch (SQLException e) {e.printStackTrace();}

        return count;
    }

    public static void addToCart(int id, int productID, String title, double price, String category)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        String query = "INSERT INTO carts (id, product_id, title, price, category) VALUES (?,?,?,?,?)";

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, productID);
            preparedStatement.setString(3, title);
            preparedStatement.setDouble(4, price);
            preparedStatement.setString(5, category);

            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("database closed");

        } catch (SQLException e) {e.printStackTrace();}
    }
    public static ResultSet getCart(int id)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result = null;
        String query = "SELECT * FROM carts WHERE id = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeQuery();  //called if return is needed
        } catch (SQLException e) {e.printStackTrace();}

        return result;
    }

    public static void removeFromCart(int id, String title)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        String query = "DELETE FROM carts WHERE id = ? AND title = ?";

        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            preparedStatement.executeUpdate();
            connection.close();
            System.out.println("database closed");

        } catch (SQLException e) {e.printStackTrace();}
    }

    public static boolean isInCart(int id, String title)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result = null;
        String query = "SELECT product_id FROM carts WHERE id = ? AND title = ?";
        boolean doesExist = false;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, title);

            result = preparedStatement.executeQuery();  //called if return is needed
            doesExist = result.next();
            connection.close();
            result.close();

        } catch (SQLException e) {e.printStackTrace();}

        return doesExist;
    }

    public static void addToOrderHistory(String paymentMethod)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        ResultSet result = null;

        try {
            PreparedStatement preparedStatement;
            String query = "SELECT * FROM carts WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Signin.id);
            result = preparedStatement.executeQuery();

            while(result.next())
            {
                query = "INSERT INTO orders (id, product_id, title, price, payment_method) VALUES (?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Signin.id);
                preparedStatement.setInt(2, result.getInt("product_id"));
                preparedStatement.setString(3, result.getString("title"));
                preparedStatement.setDouble(4, result.getDouble("price"));
                preparedStatement.setString(5, paymentMethod);

                preparedStatement.executeUpdate();
            }

            result.close();
            connection.close();
            System.out.println("database closed");

        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void checkout(double amount)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        ResultSet result = null;

        try {
            PreparedStatement preparedStatement;
            String query = "SELECT credits FROM users WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Signin.id);
            result = preparedStatement.executeQuery();

            query = "UPDATE users SET credits = ? WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDouble(1, result.getDouble("credits") - amount);
            preparedStatement.setInt(2, Signin.id);
            preparedStatement.executeUpdate();

            query = "DELETE FROM carts WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Signin.id);
            preparedStatement.executeUpdate();

            result.close();
            connection.close();
            System.out.println("database closed");

        } catch (SQLException e) {e.printStackTrace();}
    }


    public static ResultSet getProducts()
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        String query = "SELECT * FROM products";
        ResultSet resultSet = null;
        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

        } catch (SQLException e) {e.printStackTrace();}
        return resultSet;
    }
    public static void removeRow(String title)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        String query = "DELETE FROM products WHERE title = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, title);
            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {e.printStackTrace();}
    }
    public static void updateProduct(String category, String title, String infoColumn, String infoValue)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        String query = "UPDATE products SET " + infoColumn + " = ? WHERE category = ? AND title = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, infoValue);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, title);
            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {e.printStackTrace();}
    }
    public static void addProduct(String category, String title)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        String query = "INSERT INTO products (category, title) VALUES (?,?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, category);
            preparedStatement.setString(2, title);
            preparedStatement.executeUpdate();
            connection.close();

        } catch (SQLException e) {e.printStackTrace();}
    }

    public static void sendOrderDetails(String paymentMethod, double totalAmount)
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        ResultSet result = null;

        try {
            PreparedStatement preparedStatement;
            String query = "SELECT * FROM carts WHERE id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Signin.id);
            result = preparedStatement.executeQuery();
            String address = accountFunc.getUserDetail(Signin.id, "address");
            String fullName = accountFunc.getUserDetail(Signin.id, "full_name");

            LocalDate date = LocalDate.now(ZoneId.of("Asia/Dhaka"));


            String html = """                    
                    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:o="urn:schemas-microsoft-com:office:office" style="font-family:arial, 'helvetica neue', helvetica, sans-serif">
                    <head>
                    <meta charset="UTF-8">
                    <meta content="width=device-width, initial-scale=1" name="viewport">
                    <meta name="x-apple-disable-message-reformatting">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta content="telephone=no" name="format-detection">
                    <title>Orders</title><!--[if (mso 16)]>
                    <style type="text/css">
                    a {text-decoration: none;}
                    </style>
                    <![endif]--><!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]--><!--[if gte mso 9]>
                    <xml>
                    <o:OfficeDocumentSettings>
                    <o:AllowPNG></o:AllowPNG>
                    <o:PixelsPerInch>96</o:PixelsPerInch>
                    </o:OfficeDocumentSettings>
                    </xml>
                    <![endif]-->
                    <style type="text/css">
                    #outlook a {
                    padding:0;
                    }
                    .es-button {
                    mso-style-priority:100!important;
                    text-decoration:none!important;
                    }
                    a[x-apple-data-detectors] {
                    color:inherit!important;
                    text-decoration:none!important;
                    font-size:inherit!important;
                    font-family:inherit!important;
                    font-weight:inherit!important;
                    line-height:inherit!important;
                    }
                    .es-desk-hidden {
                    display:none;
                    float:left;
                    overflow:hidden;
                    width:0;
                    max-height:0;
                    line-height:0;
                    mso-hide:all;
                    }
                    [data-ogsb] .es-button {
                    border-width:0!important;
                    padding:10px 20px 10px 20px!important;
                    }
                    .es-button-border:hover a.es-button,
                    .es-button-border:hover button.es-button {
                    background:#56d66b!important;
                    border-color:#56d66b!important;
                    }
                    .es-button-border:hover {
                    border-color:#42d159 #42d159 #42d159 #42d159!important;
                    background:#56d66b!important;
                    }
                    @media only screen and (max-width:600px) {p, ul li, ol li, a { line-height:150%!important } h1, h2, h3, h1 a, h2 a, h3 a { line-height:120% } h1 { font-size:30px!important; text-align:left } h2 { font-size:24px!important; text-align:left } h3 { font-size:20px!important; text-align:left } .es-header-body h1 a, .es-content-body h1 a, .es-footer-body h1 a { font-size:30px!important; text-align:left } .es-header-body h2 a, .es-content-body h2 a, .es-footer-body h2 a { font-size:24px!important; text-align:left } .es-header-body h3 a, .es-content-body h3 a, .es-footer-body h3 a { font-size:20px!important; text-align:left } .es-menu td a { font-size:14px!important } .es-header-body p, .es-header-body ul li, .es-header-body ol li, .es-header-body a { font-size:14px!important } .es-content-body p, .es-content-body ul li, .es-content-body ol li, .es-content-body a { font-size:14px!important } .es-footer-body p, .es-footer-body ul li, .es-footer-body ol li, .es-footer-body a { font-size:14px!important } .es-infoblock p, .es-infoblock ul li, .es-infoblock ol li, .es-infoblock a { font-size:12px!important } *[class="gmail-fix"] { display:none!important } .es-m-txt-c, .es-m-txt-c h1, .es-m-txt-c h2, .es-m-txt-c h3 { text-align:center!important } .es-m-txt-r, .es-m-txt-r h1, .es-m-txt-r h2, .es-m-txt-r h3 { text-align:right!important } .es-m-txt-l, .es-m-txt-l h1, .es-m-txt-l h2, .es-m-txt-l h3 { text-align:left!important } .es-m-txt-r img, .es-m-txt-c img, .es-m-txt-l img { display:inline!important } .es-button-border { display:inline-block!important } a.es-button, button.es-button { font-size:18px!important; display:inline-block!important } .es-adaptive table, .es-left, .es-right { width:100%!important } .es-content table, .es-header table, .es-footer table, .es-content, .es-footer, .es-header { width:100%!important; max-width:600px!important } .es-adapt-td { display:block!important; width:100%!important } .adapt-img { width:100%!important; height:auto!important } .es-m-p0 { padding:0px!important } .es-m-p0r { padding-right:0px!important } .es-m-p0l { padding-left:0px!important } .es-m-p0t { padding-top:0px!important } .es-m-p0b { padding-bottom:0!important } .es-m-p20b { padding-bottom:20px!important } .es-mobile-hidden, .es-hidden { display:none!important } tr.es-desk-hidden, td.es-desk-hidden, table.es-desk-hidden { width:auto!important; overflow:visible!important; float:none!important; max-height:inherit!important; line-height:inherit!important } tr.es-desk-hidden { display:table-row!important } table.es-desk-hidden { display:table!important } td.es-desk-menu-hidden { display:table-cell!important } .es-menu td { width:1%!important } table.es-table-not-adapt, .esd-block-html table { width:auto!important } table.es-social { display:inline-block!important } table.es-social td { display:inline-block!important } .es-desk-hidden { display:table-row!important; width:auto!important; overflow:visible!important; max-height:inherit!important } }
                    </style>
                    </head>
                    <body style="width:100%;font-family:arial, 'helvetica neue', helvetica, sans-serif;-webkit-text-size-adjust:100%;-ms-text-size-adjust:100%;padding:0;Margin:0">
                    <div class="es-wrapper-color" style="background-color:#F6F6F6"><!--[if gte mso 9]>
                    <v:background xmlns:v="urn:schemas-microsoft-com:vml" fill="t">
                    <v:fill type="tile" color="#f6f6f6"></v:fill>
                    </v:background>
                    <![endif]-->
                    <table class="es-wrapper" width="100%" cellspacing="0" cellpadding="0" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;padding:0;Margin:0;width:100%;height:100%;background-repeat:repeat;background-position:center top;background-color:#F6F6F6">
                    <tr>
                    <td valign="top" style="padding:0;Margin:0">
                    <table class="es-header es-visible-simple-html-only" cellspacing="0" cellpadding="0" align="center" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;table-layout:fixed !important;width:100%;background-color:transparent;background-repeat:repeat;background-position:center top">
                    <tr>
                    <td class="es-stripe-html" align="center" style="padding:0;Margin:0">
                    <table class="es-header-body" cellspacing="0" cellpadding="0" bgcolor="#ffffff" align="center" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;background-color:#FFFFFF;width:600px">
                    <tr>
                    <td align="left" style="padding:0;Margin:0;padding-top:50px;padding-left:20px;padding-right:20px"><!--[if mso]><table style="width:560px" cellpadding="0" cellspacing="0"><tr><td style="width:270px" valign="top"><![endif]-->
                    <table cellpadding="0" cellspacing="0" class="es-left" align="left" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left">
                    <tr>
                    <td class="es-m-p20b" align="left" style="padding:0;Margin:0;width:270px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr class="es-visible-simple-html-only">
                    <td align="left" style="padding:0;Margin:0;font-size:0px"><img class="adapt-img" src="https://img.icons8.com/ios-filled/80/000000/mac-os.png" alt style="display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic" width="45" height="45"></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table><!--[if mso]></td><td style="width:20px"></td><td style="width:270px" valign="top"><![endif]-->
                    <table cellpadding="0" cellspacing="0" class="es-right" align="right" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right">
                    <tr>
                    <td align="left" style="padding:0;Margin:0;width:270px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="right" style="padding:0;Margin:0"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:30px;color:#333333;font-size:20px">Receipt</p></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table><!--[if mso]></td></tr></table><![endif]--></td>
                    </tr>
                    <tr>
                    <td align="left" style="padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px"><!--[if mso]><table style="width:560px" cellpadding="0" cellspacing="0"><tr><td style="width:227px" valign="top"><![endif]-->
                    <table cellpadding="0" cellspacing="0" class="es-left" align="left" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left">
                    <tr class="es-visible-simple-html-only">
                    <td class="es-m-p0r es-m-p20b es-container-visible-simple-html-only" align="center" style="padding:0;Margin:0;width:207px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="left" style="padding:0;Margin:0"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#333333;font-size:14px"><span style="color:#808080;font-size:13px"><span style="background-color:#FFFFFF">DATE</span></span><br><span style="font-size:15px">
                    """
                    +
                    date
                    +
                    """
                    </span><br><br><br><br></p></td>
                    </tr>
                    </table></td>
                    <td class="es-hidden" style="padding:0;Margin:0;width:20px"></td>
                    </tr>
                    </table><!--[if mso]></td><td style="width:156px" valign="top"><![endif]-->
                    <table cellpadding="0" cellspacing="0" class="es-left" align="left" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:left">
                    <tr>
                    <td class="es-m-p20b" align="center" style="padding:0;Margin:0;width:156px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="left" style="padding:0;Margin:0"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#060000;font-size:14px"><span style="color:#808080;font-size:13px"><span style="background-color:#FFFFFF">BILLED TO</span></span><br><span style="font-size:15px">
                    """
                    +
                    fullName
                    +
                    "<br>"
                    +
                    address
                    +
                    """
                    </span></p></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table><!--[if mso]></td><td style="width:20px"></td><td style="width:157px" valign="top"><![endif]-->
                    <table cellpadding="0" cellspacing="0" class="es-right" align="right" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;float:right">
                    <tr>
                    <td align="center" style="padding:0;Margin:0;width:157px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="right" style="padding:0;Margin:0"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:21px;color:#333333;font-size:14px"><span style="font-size:13px;color:#808080">TOTAL</span><br><span style="font-size:17px"><strong><span style="color:#000000">$
                    """
                    +
                    totalAmount
                    +
                    """
                    </span></strong></span></p></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table><!--[if mso]></td></tr></table><![endif]--></td>
                    </tr>
                    <tr>
                    <td align="left" style="padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="center" valign="top" style="padding:0;Margin:0;width:560px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="left" style="padding:0;Margin:0">
                    <table border="0" class="es-table cke_show_border" align="center" cellspacing="3" cellpadding="3" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px;width:500px">
                    <thead>
                    <tr>
                    <th scope="col" style="font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:13px;color:#808080;text-align:left">PRODUCT</th>
                    <th scope="col" style="font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;font-size:13px;color:#808080;text-align:left">PRICE</th>
                    </tr>
                    </thead>
                    """;
					
            while(result.next())
            {          
                html += """                        
                        <tr>
                        <td style="padding:0;Margin:0;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;color:#808080">
                        """
                        +
                        result.getString("title")
                        +
                        """
                        </td>
                        <td style="padding:0;Margin:0;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;color:#808080">$
                        """
                        +
                        result.getDouble("price")
                        +
                        """
                        </td>
                        </tr>
                        """;
            }

            html += """
                    </table></td>
                    </tr>
                    <tr>
                    <td align="center" style="padding:40px;Margin:0;font-size:0">
                    <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td style="padding:0;Margin:0;border-bottom:1px solid #cccccc;background:unset;height:1px;width:100%;margin:0px"></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table></td>
                    </tr>
                    <tr class="es-visible-simple-html-only">
                    <td class="es-struct-html" align="left" style="padding:0;Margin:0;padding-top:20px;padding-left:20px;padding-right:20px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="center" valign="top" style="padding:0;Margin:0;width:560px">
                    <table cellpadding="0" cellspacing="0" width="100%" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td align="center" style="padding:0;Margin:0"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#333333;font-size:12px">If you didn't make this purchase or if you believe an unauthorized person is attempting to access your account, please contact apple support to cancel your purchase.</p></td>
                    </tr>
                    <tr>
                    <td align="center" style="padding:15px;Margin:0;font-size:0">
                    <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td style="padding:0;Margin:0;border-bottom:0px solid #cccccc;background:unset;height:1px;width:100%;margin:0px"></td>
                    </tr>
                    </table></td>
                    </tr>
                    <tr>
                    <td align="center" style="padding:5px;Margin:0;font-size:0px"><img class="adapt-img" src="https://img.icons8.com/ios-filled/80/000000/mac-os.png" alt style="display:block;border:0;outline:none;text-decoration:none;-ms-interpolation-mode:bicubic" width="30" height="30"></td>
                    </tr>
                    <tr>
                    <td align="center" style="padding:20px;Margin:0;font-size:0">
                    <table border="0" width="100%" height="100%" cellpadding="0" cellspacing="0" style="mso-table-lspace:0pt;mso-table-rspace:0pt;border-collapse:collapse;border-spacing:0px">
                    <tr>
                    <td style="padding:0;Margin:0;border-bottom:0px solid #cccccc;background:unset;height:1px;width:100%;margin:0px"></td>
                    </tr>
                    </table></td>
                    </tr>
                    <tr>
                    <td style="padding:0;Margin:0"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:arial, 'helvetica neue', helvetica, sans-serif;line-height:21px;color:#333333;font-size:14px;text-align:center"><a href="https://support.apple.com/en-us/HT204088" style="margin-right: 5px;margin-left: 5px;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#0A84FF;font-size:14px">Apple ID Summary</a><a href="https://store.apple.com/Catalog/uk_inst/Images/salespolicies_individual.html" style="margin-right: 5px;margin-left: 5px;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#0A84FF;font-size:14px">Terms of Sale</a><a href="https://www.apple.com/legal/privacy/en-ww/" style="margin-right: 5px;margin-left: 5px;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;text-decoration:underline;color:#0A84FF;font-size:14px">Privacy Policy</a></p></td>
                    </tr>
                    <tr>
                    <td align="center" style="padding:0;Margin:0;padding-top:20px"><p style="Margin:0;-webkit-text-size-adjust:none;-ms-text-size-adjust:none;mso-line-height-rule:exactly;font-family:helvetica, 'helvetica neue', arial, verdana, sans-serif;line-height:18px;color:#333333;font-size:12px">Copyright 2022 Apple Inc.<br><strong><span style="color:#0a84ff">All rights reserved</span></strong></p></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table></td>
                    </tr>
                    </table>
                    </div>
                    </body>
                    </html>
                    """;

            result.close();
            connection.close();
            System.out.println("database closed");

            String email = accountFunc.getUserDetail(Signin.id, "email");

            String finalHtml = html;
            Thread newThread = new Thread(() -> {
                SendMail.sendMail(email, finalHtml);
            });
            newThread.start();

        } catch (SQLException e) {e.printStackTrace();}
    }

    public static ResultSet getStat()
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result = null;
        String query = "SELECT * FROM stats WHERE id = 1";

        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

        } catch (SQLException e) {e.printStackTrace();}

        return result;
    }

    public static ResultSet getCards()
    {
        Connection connection = dbFunc.getConnection("db/database.db");
        PreparedStatement preparedStatement;
        ResultSet result = null;
        String query = "SELECT * FROM gift_cards";

        try {
            preparedStatement = connection.prepareStatement(query);
            result = preparedStatement.executeQuery();

        } catch (SQLException e) {e.printStackTrace();}

        return result;
    }

}
