package cu.dev.halal.service.impl;

import cu.dev.halal.dao.ReportDAO;
import cu.dev.halal.dto.ReportDTO;
import cu.dev.halal.entity.ReportEntity;
import cu.dev.halal.entity.StoreEntity;
import cu.dev.halal.entity.UserEntity;
import cu.dev.halal.service.ReportService;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    private final static Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
    private final ReportDAO reportDAO;

    public ReportServiceImpl(
            @Autowired ReportDAO reportDAO
    ){
        this.reportDAO = reportDAO;
    }

    // ReportDTO -> ReportEntity
    @Override
    public JSONObject createReport(ReportDTO reportDTO) {
        ReportEntity reportEntity = ReportEntity.builder()
                .title(reportDTO.getTitle())
                .content(reportDTO.getContent())
                .user(UserEntity.builder().email(reportDTO.getEmail()).build())
                .store(StoreEntity.builder().id(reportDTO.getStoreId()).build())
                .build();
        JSONObject jsonObject = this.reportDAO.createReport(reportEntity);

        return jsonObject;
    }

    // ReportEntity List를 ReportDTO List로 변환 후 반환
    @Override
    public JSONObject readAllReport(String email) {
        JSONObject jsonObject = new JSONObject();
        List<ReportEntity> reportList = this.reportDAO.readAllReport(email);
        List<ReportDTO> reports = new ArrayList<>();
        try{
            for (ReportEntity reportEntity : reportList) {
                reports.add(ReportDTO.builder()
                        .id(reportEntity.getId())
                        .title(reportEntity.getTitle())
                        .content(reportEntity.getContent())
                        .email(reportEntity.getUser().getEmail())
                        .storeId(reportEntity.getStore().getId())
                        .build());
            }
            jsonObject.put("reports", reports);
            return jsonObject;
        }catch (NullPointerException e){
            jsonObject.put("result", "user or store not exists");
            return jsonObject;
        }
    }

    @Override
    public JSONObject readAllReportByStore(Long storeId) {
        JSONObject jsonObject = new JSONObject();
        List<ReportEntity> reportList = this.reportDAO.readAllReportByStore(storeId);
        List<ReportDTO> reports = new ArrayList<>();
        try{
            for (ReportEntity reportEntity : reportList) {
                reports.add(ReportDTO.builder()
                        .id(reportEntity.getId())
                        .title(reportEntity.getTitle())
                        .content(reportEntity.getContent())
                        .email(reportEntity.getUser().getEmail())
                        .storeId(reportEntity.getStore().getId())
                        .build());
            }
            jsonObject.put("reports", reports);
            return jsonObject;
        }catch (NullPointerException e){
            jsonObject.put("result", "user or store not exists");
            return jsonObject;
        }
    }


    // 보류
    @Override
    public JSONObject updateReport(ReportDTO reportDTO) {
        return null;
    }

    @Override
    public JSONObject deleteReport(Long id) {
        return this.reportDAO.deleteReport(id);
    }
}
