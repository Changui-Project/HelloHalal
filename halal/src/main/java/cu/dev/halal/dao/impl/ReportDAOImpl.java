package cu.dev.halal.dao.impl;

import cu.dev.halal.dao.ReportDAO;
import cu.dev.halal.entity.ReportEntity;
import cu.dev.halal.repository.ReportRepository;
import cu.dev.halal.repository.UserRepository;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class ReportDAOImpl implements ReportDAO {
    private final static Logger logger = LoggerFactory.getLogger(ReportDAOImpl.class);
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;

    public ReportDAOImpl(
            @Autowired UserRepository userRepository,
            @Autowired ReportRepository reportRepository
    ){
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }


    // 중복된 신고에대한 스팸 예외처리.
    // 저장
    @Transactional
    @Override
    public JSONObject createReport(ReportEntity reportEntity) {
        JSONObject jsonObject = new JSONObject();
        if(this.reportRepository.existsByContent(reportEntity.getContent())
                && this.userRepository.existsByEmail(reportEntity.getUser().getEmail())){
            jsonObject.put("result", "spamming content");
            return jsonObject;
        }
        if(this.userRepository.existsByEmail(reportEntity.getUser().getEmail())){
            reportEntity.setUser(this.userRepository.getByEmail(reportEntity.getUser().getEmail()));
            this.reportRepository.save(reportEntity);
            jsonObject.put("result", "success");
            return jsonObject;
        }else{
            jsonObject.put("result", "email not exists");
            return jsonObject;
        }
    }


    // email로 모든 신고 내역을 조회한다.
    @Override
    public List<ReportEntity> readAllReport(String email) {
        if(this.reportRepository.existsByUser(this.userRepository.getByEmail(email))){
            List<ReportEntity> reports = this.userRepository.getByEmail(email).getReports();
            return reports;
        }else{
            return new ArrayList<>();
        }

    }

    //
    @Override
    public JSONObject updateReport(ReportEntity reportEntity) {
        ReportEntity targetEntity = this.reportRepository.getById(reportEntity.getId());


        return null;
    }

    // DB에 존재하는 id인지 확인 후 삭제
    // json {"result" : "success" or "not exists"}
    @Override
    public JSONObject deleteReport(Long id) {
        JSONObject jsonObject = new JSONObject();
        if(this.reportRepository.existsById(id)){
            this.reportRepository.deleteById(id);
            jsonObject.put("result", "success");
            return jsonObject;
        }
        else{
            jsonObject.put("result", "not exists");
            return jsonObject;
        }
    }
}
