/*
 * Tencent is pleased to support the open source community by making BK-JOB蓝鲸智云作业平台 available.
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved.
 *
 * BK-JOB蓝鲸智云作业平台 is licensed under the MIT License.
 *
 * License for BK-JOB蓝鲸智云作业平台:
 * --------------------------------------------------------------------
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and
 * to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of
 * the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO
 * THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.tencent.bk.job.execute.dao;

import com.tencent.bk.job.common.constant.DuplicateHandlerEnum;
import com.tencent.bk.job.common.constant.NotExistPathHandlerEnum;
import com.tencent.bk.job.execute.common.constants.RunStatusEnum;
import com.tencent.bk.job.execute.common.constants.StepExecuteTypeEnum;
import com.tencent.bk.job.execute.model.*;
import com.tencent.bk.job.manage.common.consts.script.ScriptTypeEnum;

import java.util.List;

public interface StepInstanceDAO {
    Long addStepInstanceBase(StepInstanceBaseDTO stepInstance);

    void addScriptStepInstance(StepInstanceDTO stepInstance);

    void addFileStepInstance(StepInstanceDTO stepInstance);

    void addConfirmStepInstance(StepInstanceDTO stepInstance);

    StepInstanceBaseDTO getStepInstanceBase(long stepInstanceId);

    ScriptStepInstanceDTO getScriptStepInstance(long stepInstanceId);

    FileStepInstanceDTO getFileStepInstance(long stepInstanceId);

    ConfirmStepInstanceDTO getConfirmStepInstance(long stepInstanceId);

    List<StepInstanceBaseDTO> listStepInstanceBaseByTaskInstanceId(long taskInstanceId);

    void resetStepStatus(long stepInstanceId);

    void resetStepExecuteInfoForRetry(long stepInstanceId);

    void addStepExecuteCount(long stepInstanceId);

    void updateStepStatus(long stepInstanceId, int status);

    void updateStepStartTime(long stepInstanceId, Long startTime);

    /**
     * 更新步骤启动时间 - 仅当启动时间为空的的场景
     *
     * @param stepInstanceId 步骤实例ID
     * @param startTime      启动时间
     */
    void updateStepStartTimeIfNull(long stepInstanceId, Long startTime);

    void updateStepEndTime(long stepInstanceId, Long endTime);

    void addTaskExecuteCount(long taskInstanceId);

    void updateStepTotalTime(long stepInstanceId, long totalTime);

    void updateStepStatInfo(long stepInstanceId, int runIPNum, int successIPNum, int failIPNum);

    Long getFirstStepStartTime(long taskInstanceId);

    Long getLastStepEndTime(long taskInstanceId);

    long getAllStepTotalTime(long taskInstanceId);

    /**
     * 更新步骤的执行信息
     *
     * @param stepInstanceId 步骤实例ID
     * @param status         步骤执行状态
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param totalTime      总耗时
     */
    void updateStepExecutionInfo(long stepInstanceId, RunStatusEnum status, Long startTime, Long endTime,
                                 Long totalTime);

    /**
     * 更新步骤的执行信息
     *
     * @param stepInstanceId 步骤实例ID
     * @param status         步骤执行状态
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param totalTime      总耗时
     * @param runIPNum       运行中的ip
     * @param successIPNum   执行成功的ip
     * @param failIPNum      失败的ip
     */
    void updateStepExecutionInfo(long stepInstanceId, RunStatusEnum status, Long startTime, Long endTime,
                                 Long totalTime, Integer runIPNum, Integer successIPNum, Integer failIPNum);

    /**
     * 更新解析之后的脚本参数
     *
     * @param stepInstanceId      步骤实例ID
     * @param resolvedScriptParam 解析之后的脚本参数
     */
    void updateResolvedScriptParam(long stepInstanceId, String resolvedScriptParam);

    /**
     * 更新变量解析之后的源文件
     *
     * @param stepInstanceId      步骤实例ID
     * @param resolvedFileSources
     */
    void updateResolvedSourceFile(long stepInstanceId, List<FileSourceDTO> resolvedFileSources);

    /**
     * 更新变量解析之后的目标路径
     *
     * @param stepInstanceId     步骤实例ID
     * @param resolvedTargetPath 解析之后的目标路径
     */
    void updateResolvedTargetPath(long stepInstanceId, String resolvedTargetPath);

    /**
     * 更新确认理由
     *
     * @param stepInstanceId 步骤实例ID
     * @param confirmReason  确认理由
     */
    void updateConfirmReason(long stepInstanceId, String confirmReason);

    /**
     * 更新步骤实例操作者
     *
     * @param stepInstanceId 步骤实例ID
     * @param operator       操作者
     */
    void updateStepOperator(long stepInstanceId, String operator);

    Integer count(Long appId, List<Long> stepIdList, StepExecuteTypeEnum stepExecuteType, ScriptTypeEnum scriptType,
                  RunStatusEnum runStatus, Long fromTime, Long toTime);

    Integer countFastPushFile(Long appId, DuplicateHandlerEnum fileDupliateHandle, Boolean fileDupliateHandleNull,
                              NotExistPathHandlerEnum notExistPathHandler, Boolean notExistPathHandlerNull,
                              RunStatusEnum runStatus, Long fromTime, Long toTime);

    List<List<FileSourceDTO>> listFastPushFileSource(Long appId, DuplicateHandlerEnum fileDupliateHandle,
                                                     Boolean fileDupliateHandleNull,
                                                     NotExistPathHandlerEnum notExistPathHandler,
                                                     Boolean notExistPathHandlerNull, RunStatusEnum runStatus,
                                                     Long fromTime, Long toTime);

    /**
     * 获取前一个可执行步骤实例
     *
     * @param taskInstanceId 任务实例ID
     * @param stepInstanceId 当前步骤实例ID
     * @return 可执行步骤实例
     */
    StepInstanceBaseDTO getPreExecutableStepInstance(long taskInstanceId, long stepInstanceId);

    /**
     * 根据taskInstanceId获取一个stepInstanceId，用于快速脚本/文件任务
     *
     * @param taskInstanceId
     * @return
     */
    Long getStepInstanceId(long taskInstanceId);

    /**
     * 根据stepInstanceId获取脚本类型
     *
     * @param stepInstanceId
     * @return
     */
    Byte getScriptTypeByStepInstanceId(long stepInstanceId);
}
