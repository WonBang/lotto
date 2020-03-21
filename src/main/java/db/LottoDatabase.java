package db;

import org.json.simple.JSONObject;

import java.sql.*;

public class LottoDatabase {
    public void appendLottoInfo(JSONObject lottoInfoJson) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        //System.out.println("Lotto database \n" + lottoInfoJson.get("totSellamnt").toString());

        long totSellamnt = Long.parseLong(lottoInfoJson.get("totSellamnt").toString());
        long firstWinamnt = Long.parseLong(lottoInfoJson.get("firstWinamnt").toString());
        long firstAccumamnt = Long.parseLong(lottoInfoJson.get("firstAccumamnt").toString());
        String drwNoDate = lottoInfoJson.get("drwNoDate").toString();
        int drwNo = Integer.parseInt(lottoInfoJson.get("drwNo").toString());
        int drwtNo1 = Integer.parseInt(lottoInfoJson.get("drwtNo1").toString());
        int drwtNo2 = Integer.parseInt(lottoInfoJson.get("drwtNo2").toString());
        int drwtNo3 = Integer.parseInt(lottoInfoJson.get("drwtNo3").toString());
        int drwtNo4 = Integer.parseInt(lottoInfoJson.get("drwtNo4").toString());
        int drwtNo5 = Integer.parseInt(lottoInfoJson.get("drwtNo5").toString());
        int drwtNo6 = Integer.parseInt(lottoInfoJson.get("drwtNo6").toString());
        int bnusNo = Integer.parseInt(lottoInfoJson.get("bnusNo").toString());


        String url = "jdbc:postgresql://wonbang.asuscomm.com:25432/lotto";
        String user = "lotto";
        String password = "lotto";

        try {
            conn = DriverManager.getConnection(url, user, password);
            pstmt = conn.prepareStatement("SELECT lotto_info_append(?,?,?,?,?,?,?,?,?,?,?,?)");
            pstmt.setLong(1, totSellamnt);
            pstmt.setLong(2, firstWinamnt);
            pstmt.setLong(3, firstAccumamnt);
            pstmt.setString(4, drwNoDate);
            pstmt.setInt(5, drwNo);
            pstmt.setInt(6, drwtNo1);
            pstmt.setInt(7, drwtNo2);
            pstmt.setInt(8, drwtNo3);
            pstmt.setInt(9, drwtNo4);
            pstmt.setInt(10, drwtNo5);
            pstmt.setInt(11, drwtNo6);
            pstmt.setInt(12, bnusNo);

            rs = pstmt.executeQuery();
            //rs = st.executeQuery("SELECT lotto_info_append();");

            if(rs.next())
                System.out.println(rs.getString(1));
            /*if (rs.next())
                System.out.println(rs.getString(1));*/
        } catch (SQLException sqlEX) {
            System.out.println(sqlEX);
        } finally {
            try {
                rs.close();
                pstmt.close();
                conn.close();
            } catch (SQLException sqlEX) {
                System.out.println(sqlEX);
            }
        }
    }
}
