package hilo.contador.model.DAO;

import hilo.contador.model.Chronometer;
import hilo.contador.utils.Connection.Connect;
import hilo.contador.utils.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarkDao extends Chronometer {
        private static Connection con = null;

        private final static String INSERT = "INSERT INTO mark(id,hour,minute,second,millisecond) VALUES (NULL,?,?,?,?)";
        private final static String SELECTALL = "SELECT id,hour,minute,second,millisecond FROM mark";

        public MarkDao(){
            super();
        }
        public MarkDao(int id, int hour, int minute, int second, int millisecond){
            super(id,hour,minute,second,millisecond);
        }
        public MarkDao(int hour, int minute, int second, int millisecond){
            super(hour,minute,second,millisecond);
        }

        public void save() {
            con = Connect.getConnect();
            if (con != null){
                try {
                    PreparedStatement st = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                    st.setInt(1,this.hour);
                    st.setInt(2,this.minute);
                    st.setInt(3,this.second);
                    st.setInt(4,this.milisecond);
                    st.executeUpdate();
                    st.close();
                } catch (SQLException e) {
                    Log.severe("Error al insertar marca: " + e.getMessage());
                }
            }
        }

        public List<Chronometer> getAll(){
                List<Chronometer> marks = new ArrayList<>();
                con = Connect.getConnect();
                if (con != null){
                    try {
                        PreparedStatement st = con.prepareStatement(SELECTALL);
                        if (st.execute()){
                            ResultSet rs = st.executeQuery();
                            while (rs.next()){
                                MarkDao mark = new MarkDao(rs.getInt("id"),rs.getInt("hour"),rs.getInt("minute"),rs.getInt("second"),rs.getInt("millisecond"));
                                marks.add(mark);
                            }
                            rs.close();
                        }
                        st.close();
                    } catch (SQLException e) {
                        Log.severe("Error al obtener todas las marcas: " + e.getMessage());
                    }
                }
                return marks;
        }
}
