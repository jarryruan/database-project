package entity;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import util.MysqlConnection;

public class ComplaintManager {
    /* 单例模式，后续的实体管理器请按照这个格式设计 */
    public ComplaintManager() {
    }

    private static class SingletonFactory {
        private static ComplaintManager instance = new ComplaintManager();
    }

    public static ComplaintManager getInstance() {
        return SingletonFactory.instance;
    }

    public List<Complaint> get() {
        String selectSql = "SELECT * FROM complaint";
        Object o = MysqlConnection.select(selectSql, rs -> {
            List<Complaint> complaints = new ArrayList<>();
            while (rs.next()) {
                complaints.add(new Complaint(rs.getInt("complaint_ID"), rs.getString("type"), rs.getTimestamp("time"), rs.getString("description"), rs.getInt("household_id"), rs.getTimestamp("schedule"), rs.getString("outcome"), rs.getTimestamp("outcome_time")));
            }
            return complaints;
        });

        return (List<Complaint>)o;
    }

    public List<Complaint> get(Integer beginYear, Integer beginMonth, Integer endYear, Integer endMonth) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        Timestamp begin = null;
        Timestamp end = null;
        try{
            begin = new Timestamp(format.parse(beginYear + "-" + beginMonth +"-1 00:00:00").getTime());
            end = new Timestamp(format.parse(endYear + "-" + endMonth+"-30 00:00:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String selectSql = "SELECT * FROM complaint WHERE time between ? and ?";
        Object[] params = new Object[2];
        params[0] = begin;
        params[1] = end;
        Object o = MysqlConnection.select(selectSql, rs -> {
            List<Complaint> complaints = new ArrayList<>();
            while (rs.next()) {
                complaints.add(new Complaint(rs.getInt("complaint_ID"), rs.getString("type"), rs.getTimestamp("time"), rs.getString("description"), rs.getInt("household_id"), rs.getTimestamp("schedule"), rs.getString("outcome"), rs.getTimestamp("outcome_time")));
            }
            return complaints;
        }, params);
        return (List<Complaint>)o;
    }

    public List<Complaint> get(Integer beginYear, Integer beginMonth, Integer endYear, Integer endMonth, String community_name, Integer unit_ID,Integer house_ID,Integer household_ID,String type){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setLenient(false);
        Timestamp begin = null;
        Timestamp end = null;
        try{
            begin = new Timestamp(format.parse(beginYear + "-" + beginMonth +"-1 00:00:00").getTime());
            end = new Timestamp(format.parse(endYear + "-" + endMonth+"-30 00:00:00").getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String selectSql = "SELECT * FROM complaint natural join household natural join house natural join community WHERE time between '" + begin + "' and '" + end + "' ";
        if(null != community_name) selectSql += " and community_name='" + community_name + "'";
        if(null != unit_ID) selectSql += " and unit_ID='" + unit_ID + "'";
        if(null != house_ID) selectSql += " and house_ID='" + house_ID + "'";
        if(null != household_ID) selectSql += " and household_ID='" + household_ID + "'";
        if(null != type) selectSql += " and type='" + type + "'";
        Object o = MysqlConnection.select(selectSql, rs -> {
            List<Complaint> complaints = new ArrayList<>();
            while (rs.next()){
                complaints.add(new Complaint(rs.getInt("complaint_ID"), rs.getString("type"), rs.getTimestamp("time"), rs.getString("description"), rs.getInt("household_id"), rs.getTimestamp("schedule"), rs.getString("outcome"), rs.getTimestamp("outcome_time")));
            }
            return complaints;
        });
        return (List<Complaint>)o;
     }



    public boolean insert(Complaint complaint) {
        String insertSql = "INSERT INTO complaint (complaint_ID, type, time, description, household_id, schedule, outcome, outcome_time) values(?,?,?,?,?,?,?,?)";
        Object[] params = new Object[8];
        params[0] = complaint.getId();
        params[1] = complaint.getType();
        params[2] = complaint.getTime();
        params[3] = complaint.getDescription();
        params[4] = complaint.getHouseholdId();
        params[5] = complaint.getSchedule();
        params[6] = complaint.getOutcome();
        params[7] = complaint.getOutcomeTime();
        MysqlConnection.executeUpdate(insertSql, params);
        return true;
    }

    public boolean update(Complaint complaint) {
        String updateSql = "UPDATE complaint set ";
        Integer ID = complaint.getId();
        String type = complaint.getType();
        Timestamp time = complaint.getTime();
        String description = complaint.getDescription();
        Integer householdId = complaint.getHouseholdId();
        Timestamp schedule = complaint.getSchedule();
        String outcome = complaint.getOutcome();
        Timestamp outcomeTime = complaint.getOutcomeTime();
        if (null != type) updateSql += "type='" + type + "' ,";
        if (null != time) updateSql += "time='" + time + "' ,";
        if (null != description) updateSql += "description='" + description + "' ,";
        if (null != householdId) updateSql += "household_id='" + householdId + "' ,";
        if (null != schedule) updateSql += "schedule='" + schedule + "' ,";
        if (null != outcome) updateSql += "outcome='" + schedule + "' ,";
        if (null != outcomeTime) updateSql += "outcome_time'" + outcomeTime + "' ,";
        updateSql += updateSql.substring(0, updateSql.length() - 1 ) + " WHERE complaint_ID = '" + ID + "'";
        MysqlConnection.executeUpdate(updateSql);
        return true;
    }
}
