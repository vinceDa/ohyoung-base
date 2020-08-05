package com.ohyoung.system.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.google.common.collect.ImmutableMap;
import com.ohyoung.exception.RequestValidationFailedException;
import com.ohyoung.system.domain.Job;
import com.ohyoung.system.domain.dto.DepartmentDTO;
import com.ohyoung.system.domain.dto.JobDTO;
import com.ohyoung.system.domain.query.JobQueryCriteria;
import com.ohyoung.system.domain.vo.JobVO;
import com.ohyoung.system.service.DepartmentService;
import com.ohyoung.system.service.JobService;
import com.ohyoung.util.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * @author vince
 */
@Slf4j
@Api(tags = "系统管理: 岗位管理")
@Controller
@RequestMapping("api/v1/system")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询所有岗位")
    @GetMapping(value = "/jobs")
    @PreAuthorize("hasAuthority('job_all')")
    public ResponseEntity<Object> listAll(JobQueryCriteria jobQueryCriteria, Pageable pageable) {
        Long userId = SecurityUtil.getJwtUserId();
        jobQueryCriteria.setCreateBy(userId);
        List<JobDTO> jobDTOS;
        if (BeanUtil.isEmpty(jobQueryCriteria)) {
            jobDTOS = jobService.listAll();
        } else {
            jobDTOS = jobService.listAll(jobQueryCriteria.toSpecification());
        }
        List<JobVO> convert = Convert.convert(new TypeReference<List<JobVO>>() {
        }, jobDTOS);
        return ResponseEntity.ok(convert);
    }

    @ApiOperation(value = "分页查询岗位")
    @GetMapping(value = "/jobs/paging")
    @PreAuthorize("hasAuthority('job_list')")
    public ResponseEntity<Object> listForPage(JobQueryCriteria jobQueryCriteria, Pageable pageable) {
        Long userId = SecurityUtil.getJwtUserId();
        jobQueryCriteria.setCreateBy(userId);
        Page<Job> jobs;
        if (BeanUtil.isEmpty(jobQueryCriteria)) {
            jobs = jobService.listPageable(pageable);
        } else {
            jobs = jobService.listPageable(jobQueryCriteria.toSpecification(), pageable);
        }
        List<Job> content = jobs.getContent();
        List<JobVO> convert = Convert.convert(new TypeReference<List<JobVO>>() {
        }, content);
        for (JobVO single : convert) {
            if (single.getDepartmentId() != null) {
                DepartmentDTO byId = departmentService.getById(single.getDepartmentId());
                if (byId == null) {
                    log.warn("job listForPage, error departmentId: {}", single.getDepartmentId());
                } else {
                    single.setDepartmentName(byId.getName());
                }
            }
        }
        Page<JobVO> roleVOPage = new PageImpl<>(convert, PageRequest.of(jobs.getNumber(), jobs.getSize()), jobs.getTotalElements());
        return ResponseEntity.ok(roleVOPage);
    }

    @ApiOperation(value = "新增岗位")
    @PostMapping(value = "/jobs")
    @PreAuthorize("hasAuthority('job_add')")
    public ResponseEntity<Object> insert(@Validated(JobDTO.Insert.class) @RequestBody JobDTO jobDTO) {
        if (jobDTO.getDepartmentId() != null) {
            Boolean existsById = departmentService.existsById(jobDTO.getDepartmentId());
            if (!existsById) {
                log.error("insert job error: unknown departmentId");
                throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "未知的部门"));
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(jobService.insert(jobDTO));
    }

    @ApiOperation(value = "修改岗位")
    @PutMapping(value = "/jobs")
    @PreAuthorize("hasAuthority('job_update')")
    public ResponseEntity<Object> put(@Validated(JobDTO.Update.class) @RequestBody JobDTO jobDTO) {
        Boolean existsById = departmentService.existsById(jobDTO.getDepartmentId());
        if (!existsById) {
            log.error("update job error: unknown departmentId");
            throw new RequestValidationFailedException(ImmutableMap.of("errorMessage", "未知的部门"));
        }
        jobService.update(jobDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "删除岗位")
    @DeleteMapping(value = "/jobs/{id}")
    @PreAuthorize("hasAuthority('job_delete')")
    public ResponseEntity<Object> delete(@NotNull @PathVariable Long id) {
        jobService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "根据部门id获取岗位信息")
    @GetMapping(value = "/departments/{id}/jobs")
    @PreAuthorize("hasAuthority('department_job')")
    public ResponseEntity<Object> listJob(@NotNull @PathVariable Long id) {
        return ResponseEntity.ok(jobService.listJobs(id));
    }
}
