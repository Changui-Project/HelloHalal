package cu.dev.halal.dao;


import cu.dev.halal.entity.ReportEntity;
import org.json.simple.JSONObject;

import java.util.List;

public interface ReportDAO {
    public JSONObject createReport(ReportEntity reportEntity);
    // Report정보를 email 기반으로 모두 가져온다.
    public List<ReportEntity> readAllReport(String email);
    // 보류
    public JSONObject updateReport(ReportEntity reportEntity);
    public JSONObject deleteReport(Long id);

}
