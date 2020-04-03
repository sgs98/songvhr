<template>
    <div>
        <div>
           <div style="display: flex; justify-content: space-between;">
               <div>
                   <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..."
                             @clear="initEmployee"
                             prefix-icon="el-icon-search"
                             style="width: 350px;margin-right: 10px"
                             clearable
                             :clear="initEmployee"
                             v-model="keywords"
                             @keydown.enter.native="initEmployee"
                             :disabled="showAdvanceSearchView"
                   >
                   </el-input>
                   <el-button :disabled="showAdvanceSearchView" icon="el-icon-search" type="primary" @click="initEmployee" ><!--:disabled="showAdvanceSearchView"-->
                       搜索
                   </el-button>
                   <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
                       <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                          aria-hidden="true"></i>
                       高级搜索
                   </el-button>
               </div>
               <div>
                   <el-upload style="display: inline-flex;margin-right: 10px"
                              :show-file-list="false"
                              :before-upload="beforeUpload"
                              :on-success="onSuccess"
                              :on-error="onError"
                              :disabled="importDataDisabled"
                              action="/emp/basic/import"><!--@click="importData"-->
                       <el-button type="success" :disabled="importDataDisabled" :icon="importDataBtnIcon">
                           {{importDataBtnText}}
                       </el-button>
                   </el-upload>
                   <el-button type="success" @click="exportData" icon="el-icon-download">
                       导出数据
                   </el-button>
                   <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">
                       添加用户
                   </el-button>
               </div>
           </div>
            <transition name="slide-fade">
            <div v-show="showAdvanceSearchView" style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;
            padding:5px 0;margin: 10px 0 ">
                <el-row style="margin-left: 8px">
                    <el-col :span="5">
                        <el-tag style="margin-right: 8px">政治面貌:</el-tag>
                        <el-select v-model="searchValue.politicid" placeholder="政治面貌" size="mini"
                                   style="width: 130px;">
                            <el-option
                                    v-for="item in politicsstatus"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="4">
                        <el-tag style="margin-right: 8px"> 民族:</el-tag>
                        <el-select v-model="searchValue.nationid" placeholder="民族" size="mini"
                                   style="width: 130px;">
                            <el-option
                                    v-for="item in nations"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="4">
                        <el-tag style="margin-right: 8px">职位:</el-tag>
                        <el-select v-model="searchValue.posid" placeholder="职位" size="mini" style="width: 150px;">
                            <el-option
                                    v-for="item in positions"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="4">
                        <el-tag style="margin-right: 8px">职称:</el-tag>
                        <el-select v-model="searchValue.joblevelid" placeholder="职称" size="mini"
                                   style="width: 130px;">
                            <el-option
                                    v-for="item in joblevels"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </el-col>
                    <el-col :span="7">
                        <el-tag style="margin-right: 8px">聘用形式:</el-tag>
                        <el-radio-group v-model="searchValue.engageform">
                            <el-radio label="劳动合同">劳动合同</el-radio>
                            <el-radio label="劳务合同">劳务合同</el-radio>
                        </el-radio-group>
                    </el-col>
                </el-row>
                <el-row style="margin-top: 10px;margin-left: 8px">
                    <el-col :span="5">
                        <el-tag style="margin-right: 8px;">所属部门:</el-tag>
                        <el-popover
                                placement="right"
                                title="请选择部门"
                                width="200"
                                trigger="manual"
                                v-model="popVisible2">
                            <el-tree default-expand-all :data="allDeps" :props="defaultProps"
                                     @node-click="searchViewHandleNodeClick"></el-tree>
                            <div slot="reference"
                                 style="width: 130px;display: inline-flex;font-size: 13px;border: 1px solid #dedede;height: 26px;border-radius: 5px;cursor: pointer;align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px"
                                 @click="showDeptView2">{{inputDepName}}
                            </div>
                        </el-popover>
                    </el-col>
                    <el-col :span="10">
                        <el-tag style="margin-right: 8px">入职日期:</el-tag>
                        <el-date-picker
                                v-model="searchValue.begindatescope"
                                type="daterange"
                                size="mini"
                                unlink-panels
                                value-format="yyyy-MM-dd"
                                range-separator="至"
                                start-placeholder="开始日期"
                                end-placeholder="结束日期">
                        </el-date-picker>
                    </el-col>
                    <el-col :span="5" :offset="4">
                        <el-button size="mini">取消</el-button>
                        <el-button size="mini" icon="el-icon-search" type="primary" @click="initEmployee('advanced')">搜索</el-button>
                    </el-col>
                </el-row>
            </div>
            </transition>
        </div>
        <div style="margin-top: 10px">
            <el-table
                    :data="emps"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        fixed
                        align="left"
                        label="姓名"
                        width="90">
                </el-table-column>
                <el-table-column
                        prop="workid"
                        label="工号"
                        align="left"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        align="left"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        width="95"
                        align="left"
                        label="出生日期">
                </el-table-column>
                <el-table-column
                        prop="idcard"
                        width="150"
                        align="left"
                        label="身份证号码">
                </el-table-column>
                <el-table-column
                        prop="wedlock"
                        width="70"
                        label="婚姻状况">
                </el-table-column>
                <el-table-column
                        prop="nation.name"
                        width="50"
                        label="民族">
                </el-table-column>
                <el-table-column
                        prop="nativeplace"
                        width="80"
                        label="籍贯">
                </el-table-column>
                <el-table-column
                        prop="politicsstatus.name"
                        label="政治面貌">
                </el-table-column>
                <el-table-column
                        prop="email"
                        width="180"
                        align="left"
                        label="电子邮件">
                </el-table-column>
                <el-table-column
                        prop="phone"
                        width="100"
                        align="left"
                        label="电话号码">
                </el-table-column>
                <el-table-column
                        prop="address"
                        width="220"
                        align="left"
                        label="联系地址">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        width="100"
                        align="left"
                        label="所属部门">
                </el-table-column>
                <el-table-column
                        prop="position.name"
                        width="100"
                        label="职位">
                </el-table-column>
                <el-table-column
                        prop="jobLevel.name"
                        width="100"
                        label="职称">
                </el-table-column>
                <el-table-column
                        prop="engageform"
                        width="100"
                        align="left"
                        label="聘用形式">
                </el-table-column>
                <el-table-column
                        prop="tiptopdegree"
                        width="80"
                        align="left"
                        label="最高学历">
                </el-table-column>
                <el-table-column
                        prop="specialty"
                        width="150"
                        align="left"
                        label="专业">
                </el-table-column>
                <el-table-column
                        prop="school"
                        width="150"
                        align="left"
                        label="毕业院校">
                </el-table-column>
                <el-table-column
                        prop="begindate"
                        width="95"
                        align="left"
                        label="入职日期">
                </el-table-column>
                <el-table-column
                        prop="conversiontime"
                        width="95"
                        align="left"
                        label="转正日期">
                </el-table-column>
                <el-table-column
                        prop="begincontract"
                        width="95"
                        align="left"
                        label="合同起始日期">
                </el-table-column>
                <el-table-column
                        prop="endcontract"
                        width="95"
                        align="left"
                        label="合同截止日期">
                </el-table-column>
                <el-table-column
                        width="100"
                        align="left"
                        label="合同期限">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractterm}}</el-tag>
                        年
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditEmpView(scope.row)" style="padding: 3px" size="mini">编辑</el-button>
                        <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>
                        <el-button @click="deleteEmp(scope.row)" style="padding: 3px" size="mini" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display:flex;justify-content:flex-end ">
                <el-pagination
                        background
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        :total="total">
                </el-pagination>
            </div>
        </div><!--:title="title"-->
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="80%">
            <div>
                <el-form :rules="rules" :model="emp" ref="empForm">
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="姓名:" prop="name">
                                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="emp.name"
                                          placeholder="请输入员工姓名"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="性别:" prop="gender">
                                <el-radio-group v-model="emp.gender">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio label="女">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出生日期:" prop="birthday">
                                <el-date-picker
                                        v-model="emp.birthday"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 150px;"
                                        placeholder="出生日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="政治面貌:" prop="politicid">
                                <el-select v-model="emp.politicid" placeholder="政治面貌" size="mini" style="width: 200px;">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族:" prop="nationId">
                                <el-select v-model="emp.nationid" placeholder="民族" size="mini" style="width: 150px;">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="籍贯:" prop="nativeplace">
                                <el-input size="mini" style="width: 120px" prefix-icon="el-icon-edit"
                                          v-model="emp.nativeplace" placeholder="请输入籍贯"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="电子邮箱:" prop="email">
                                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-message"
                                          v-model="emp.email" placeholder="请输入电子邮箱"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="联系地址:" prop="address">
                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                                          v-model="emp.address" placeholder="请输入联系地址"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="职位:" prop="posid">
                                <el-select v-model="emp.posid" placeholder="职位" size="mini" style="width: 150px;">
                                    <el-option
                                            v-for="item in positions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="职称:" prop="joblevelid">
                                <el-select v-model="emp.joblevelid" placeholder="职称" size="mini" style="width: 150px;">
                                    <el-option
                                            v-for="item in joblevels"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属部门:" prop="departmentid">
                                <el-popover
                                        placement="right"
                                        title="请选择部门"
                                        width="200"
                                        trigger="manual"
                                        v-model="popVisible">
                                        <el-tree default-expand-all :data="allDeps" :props="defaultProps"
                                                 @node-click="handleNodeClick"></el-tree>
                                    <div @click="showDeptView" slot="reference"
                                         style="width: 150px;display: inline-flex;
                                         font-size: 13px;border: 1px solid #dedede;
                                         height: 26px;border-radius: 5px;cursor: pointer;
                                         align-items: center;padding-left: 8px;
                                          box-sizing: border-box"
                                    >{{inputDepName}}
                                    </div>
                                </el-popover>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="电话号码:" prop="phone">
                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-phone"
                                          v-model="emp.phone" placeholder="电话号码"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号:" prop="workid">
                                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                                          v-model="emp.workid" placeholder="工号" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="学历:" prop="tiptopdegree">
                                <el-select v-model="emp.tiptopdegree" placeholder="学历" size="mini"
                                           style="width: 150px;">
                                    <el-option
                                            v-for="item in tiptopdegrees"
                                            :key="item"
                                            :label="item"
                                            :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="毕业院校:" prop="school">
                                <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit"
                                          v-model="emp.school" placeholder="毕业院校名称"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="专业名称:" prop="specialty">
                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit"
                                          v-model="emp.specialty" placeholder="请输入专业名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职日期:" prop="begindate">
                                <el-date-picker
                                        v-model="emp.begindate"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="入职日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="转正日期:" prop="conversiontime">
                                <el-date-picker
                                        v-model="emp.conversiontime"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="转正日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同起始日期:" prop="begincontract">
                                <el-date-picker
                                        v-model="emp.begincontract"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="合同起始日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同终止日期:" prop="endcontract">
                                <el-date-picker
                                        v-model="emp.endcontract"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 150px;"
                                        placeholder="合同终止日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码:" prop="idcard">
                                <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                          v-model="emp.idcard" placeholder="请输入身份证号码"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="聘用形式:" prop="engageform">
                                <el-radio-group v-model="emp.engageform">
                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                    <el-radio label="劳务合同">劳务合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="婚姻状况:" prop="wedlock">
                                <el-radio-group v-model="emp.wedlock">
                                    <el-radio label="已婚">已婚</el-radio>
                                    <el-radio label="未婚">未婚</el-radio>
                                    <el-radio label="离异">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddEmp">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data(){
            return{
                rules: {
                    name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    gender: [{required: true, message: '请输入性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
                    idcard: [{required: true, message: '请输入身份证号码', trigger: 'blur'}, {
                        pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
                        message: '身份证号码格式不正确',
                        trigger: 'blur'
                    }],
                    wedlock: [{required: true, message: '请输入婚姻状况', trigger: 'blur'}],
                    nationid: [{required: true, message: '请输入您组', trigger: 'blur'}],
                    nativeplace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    politicid: [{required: true, message: '请输入政治面貌', trigger: 'blur'}],
                    email: [{required: true, message: '请输入邮箱地址', trigger: 'blur'}, {
                        type: 'email',
                        message: '邮箱格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入电话号码', trigger: 'blur'}],
                    address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
                    departmentid: [{required: true, message: '请输入部门名称', trigger: 'blur'}],
                    joblevels: [{required: true, message: '请输入职称', trigger: 'blur'}],
                    posid: [{required: true, message: '请输入职位', trigger: 'blur'}],
                    engageform: [{required: true, message: '请输入聘用形式', trigger: 'blur'}],
                    tiptopdegree: [{required: true, message: '请输入学历', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    begindate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
                    workstate: [{required: true, message: '请输入工作状态', trigger: 'blur'}],
                    workid: [{required: true, message: '请输入工号', trigger: 'blur'}],
                    contractterm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
                    conversiontime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
                    notworkdate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
                    begincontract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
                    endcontract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
                    workage: [{required: true, message: '请输入工龄', trigger: 'blur'}],
                },
                searchValue:{
                    politicid: null,
                    nationid: null,
                    joblevelid: null,
                    posid: null,
                    engageform: null,
                    departmentid: null,
                    begindatescope: null
                },
                importDataBtnText:'导入数据',
                importDataBtnIcon:'el-icon-upload2',
                importDataDisabled:false,
                popVisible:false,
                popVisible2:false,
                title:'',
                inputDepName:'',
                visible:false,
                emps:[],
                loading:false,
                total:0,
                page:1,
                size:10,
                keywords:'',
                nations:[],
                joblevels:[],
                politicsstatus:[],
                positions:[],
                allDeps:[],
                showAdvanceSearchView:false,
                emp: {
                    name: "javaboy",
                    gender: "男",
                    birthday: "1989-12-31",
                    idcard: "610122199001011256",
                    wedlock: "已婚",
                    nationid: 1,
                    nativeplace: "陕西",
                    politicid: 13,
                    email: "laowang@qq.com",
                    phone: "18565558897",
                    address: "深圳市南山区",
                    departmentid: null,
                    joblevelid: 9,
                    posid: 29,
                    engageform: "劳务合同",
                    tiptopdegree: "本科",
                    specialty: "信息管理与信息系统",
                    school: "深圳大学",
                    begindate: "2017-12-31",
                    workstate: "在职",
                    workid: "",
                    contractterm: 2,
                    conversiontime: "2018-03-31",
                    notworkdate: null,
                    begincontract: "2017-12-31",
                    endcontract: "2019-12-31",
                    workage: null
                },
                dialogVisible:false,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                tiptopdegrees:['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
            }
        },
        methods:{
            searchViewHandleNodeClick(data){
                this.inputDepName=data.name
                this.searchValue.departmentid=data.id
                this.popVisible2=false
            },
            onError(err, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
            },
            onSuccess(response, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
                this.initEmployee()
            },
            beforeUpload() {
                this.importDataBtnText = '正在导入';
                this.importDataBtnIcon = 'el-icon-loading';
                this.importDataDisabled = true;
            },
            exportData(){
                window.open("/emp/basic/export","_parent")
            },
            showEditEmpView(data){
                this.title="编辑员工信息"
                this.emp=data
                this.dialogVisible = true;
                this.inputDepName=data.department.name
            },
            deleteEmp(data){
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/emp/basic/" + data.id).then(res => {
                        if (res) {
                            this.initEmployee();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            emptyEmp(){
                this.emp={
                    name: "",
                    gender: "",
                    birthday: "",
                    idcard: "",
                    wedlock: "",
                    nationid: null,
                    nativeplace: "",
                    politicid: null,
                    email: "",
                    phone: "",
                    address: "",
                    departmentid: null,
                    joblevelid: "",
                    posid: "",
                    engageform: "",
                    tiptopdegree: "",
                    specialty: "",
                    school: "",
                    begindate: "",
                    workid: "",
                    contractterm: "",
                    conversiontime: "",
                    notworkdate: null,
                    begincontract: "",
                    endcontract: "",
                    workage: null
                }
                this.inputDepName=""
            },
            doAddEmp(){
                if(this.emp.id){
                    this.$refs['empForm'].validate(valid => {
                        if (valid) {
                            this.putRequest("/emp/basic/", this.emp).then(res => {
                                if (res) {
                                    this.dialogVisible = false;
                                    this.initEmployee();
                                }
                            })
                        }
                    });
                }else{
                this.$refs['empForm'].validate(valid => {
                    if (valid) {
                        this.postRequest("/emp/basic/", this.emp).then(res => {
                            if (res) {
                                this.dialogVisible = false;
                                this.initEmployee();
                            }
                        })
                    }
                });
                }
            },
            handleNodeClick(data){
                this.popVisible=false
                this.inputDepName=data.name
                this.emp.departmentid=data.id

            },
            showDeptView(){
                this.popVisible = !this.popVisible
            },
            showDeptView2(){
                this.popVisible2 = !this.popVisible2
                this.initDept()
            },
            getMaxWordID(){
                this.getRequest("/emp/basic/maxWordID/").then(res=>{
                    if(res){
                    this.emp.workid=res.obj
                    }
                })
            },
            initData(){
                    this.getRequest("/emp/basic/nations/").then(res=>{
                        if(res){
                        this.nations=res
                        }
                    })
                    this.getRequest("/emp/basic/jobLevels/").then(res=>{
                        if(res) {
                            this.joblevels = res
                        }
                    })
                    this.getRequest("/emp/basic/politicsstatus/").then(res=>{
                        if(res) {
                            this.politicsstatus = res
                        }
                    })

            },
            initDept(){
                this.getRequest("/system/basic/department/").then(resp => {
                    if (resp) {
                        this.allDeps = resp;
                    }
                })
            },
            initPosition(){
                this.getRequest("/emp/basic/position/").then(res=>{
                    this.positions=res
                })
            },
            showAddEmpView(data) {
                this.title="添加员工信息"
                this.initPosition()
                this.initDept()
                this.getMaxWordID()
                this.emptyEmp()
                this.dialogVisible = true;

            },
            currentChange(currentPage){
                this.page=currentPage
                this.initEmployee()
            },
            sizeChange(sizeChange){
                this.size=sizeChange
                this.initEmployee()
            },
            initEmployee(type){
                this.loading=true

                let url="/emp/basic/?page="+this.page+"&size="+this.size

                if(type&&type=="advanced"){
                        if (this.searchValue.politicid) {
                            url += '&politicid=' + this.searchValue.politicid;
                        }
                        if (this.searchValue.nationid) {
                            url += '&nationid=' + this.searchValue.nationid;
                        }
                        if (this.searchValue.joblevelid) {
                            url += '&joblevelid=' + this.searchValue.joblevelid;
                        }
                        if (this.searchValue.posid) {
                            url += '&posid=' + this.searchValue.posid;
                        }
                        if (this.searchValue.engageform) {
                            url += '&engageform=' + this.searchValue.engageform;
                        }
                        if (this.searchValue.departmentid) {
                            url += '&departmentid=' + this.searchValue.departmentid;
                        }
                        if (this.searchValue.begindatescope) {
                            url += '&begindatescope=' + this.searchValue.begindatescope;
                        }
                }else{
                    url+="&name="+this.keywords
                }

                this.getRequest(url).then(res=>{
                   this.loading=false
                   if(res){
                       this.emps=res.data
                       this.total=res.total
                   }

               })
            }
        },
        mounted(){
            this.initEmployee()
            this.initData()
            this.initPosition()
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>
