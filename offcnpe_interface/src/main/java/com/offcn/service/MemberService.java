package com.offcn.service;

import com.offcn.util.Result;

import java.util.List;

public interface MemberService {
    /**
     * 获取会员统计列表
     * @return
     */
    public Result getMemberReport();

    /**
     * 获取指定月的会员数量
     * @param months
     * @return
     */
    public List<Integer> findMembersByMonths(List<String> months);
}
