<template>
    <div>
        <div style="display: flex; justify-content: space-between;">
            <div>
                <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..."
                          @clear="initEmpMove"
                          prefix-icon="el-icon-search"
                          style="width: 350px;margin-right: 10px"
                          clearable
                          :clear="initEmpMove"
                          v-model="keywords"
                          @keydown.enter.native="initEmpMove"
                >
                </el-input>
                <el-button icon="el-icon-search" type="primary" @click="initEmpMove" >
                    搜索
                </el-button>
            </div>
            <div>
                <el-button type="primary" icon="el-icon-plus" @click="showAddView">
                    调动员工
                </el-button>
            </div>
        </div>
        <div style="margin-top: 8px">
            <el-table :data="empMove" stripe
                      border
                      style="width: 100%">
                <el-table-column
                        prop="employee.name"
                        fixed
                        align="left"
                        label="姓名"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        label="调动前部门"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="jobLevel.name"
                        label="调动前职称"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="afterdepid"
                        label="调动后部门"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="afterjobid"
                        label="调动后职称"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="removedate"
                        label="调动时间"
                        align="center"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="reason"
                        label="调动原因"
                        align="center"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        align="center"
                        width="200">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="220"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditView(scope.row)" style="padding: 3px">编辑</el-button>
                        <el-button @click="deleteEmpMove(scope.row)" style="padding: 3px" type="danger">删除
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
        </div>
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="60%">

            <div>
                <el-form :rules="rules" :model="empMoves" ref="empForm">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="姓名:" prop="name">

                                <div style="display: inline-flex;">
                                    <el-input size="mini" style="width: 200px; display: none" prefix-icon="el-icon-edit" v-model="empMoves.eid"
                                              placeholder="请输入员工姓名"></el-input>
                                    <el-input size="mini" style="width: 250px" prefix-icon="el-icon-edit"
                                              v-model="inputName" readonly placeholder="请输入员工姓名"></el-input>
                                    <el-button type="primary" style="margin-left: 8px" size="mini" @click="showEmp">选择员工</el-button>
                                </div>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="员工调动前部门:" prop="name">
                                <el-input size="mini" style="width: 200px" readonly prefix-icon="el-icon-edit" v-model="beforeDep"
                                          placeholder="请输入员工之前部门"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="员工调动前职称:" prop="name">
                                <el-input size="mini" style="width: 200px" readonly prefix-icon="el-icon-edit" v-model="beforeJob"
                                          placeholder="请输入员工之前职称"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="员工调动后部门:" prop="afterdepid">
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
                    </el-row>
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="员工调动后职称:" prop="afterjobid">
                                <el-select v-model="empMoves.afterjobid" placeholder="请输入员工调动后职称" size="mini" style="width: 150px;">
                                    <el-option
                                            v-for="item in joblevels"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.name">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="调动原因:" prop="reason">
                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit" v-model="empMoves.reason"
                                          placeholder="请输入调动原因"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="13">
                            <el-form-item label="备注:" prop="remark">
                                <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit" v-model="empMoves.remark"
                                          placeholder="请输入备注"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addEmpMove">确 定</el-button>
        </el-dialog>
        <el-dialog
                :visible.sync="dialogVisible2"
                width="60%" height="50%">
            <el-table :data="emp.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
                      stripe
                      border>
                <el-table-column
                        prop="name"
                        fixed
                        align="left"
                        label="姓名"
                        width="90">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        align="center"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        label="出生日期"
                        align="center"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="dname"
                        label="部门名称"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="jname"
                        label="职称"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="idcard"
                        label="身份证"
                        align="center"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="email"
                        label="电子邮件"
                        align="center"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="phone"
                        width="100"
                        align="center"
                        label="电话号码">
                </el-table-column>
                <el-table-column
                        prop="address"
                        width="220"
                        align="center"
                        label="联系地址">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        align="center"
                        label="操作">
                    <template slot="header" slot-scope="scope">
                        <el-input
                                v-model="search"
                                size="mini"
                                placeholder="输入关键字搜索"/>
                    </template>
                    <template slot-scope="scope">
                        <el-button type="primary"  @click="choose(scope.row)" style="padding: 3px">选择</el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display:flex;justify-content:flex-end ">
                <el-pagination
                        background
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        @current-change="currentChange2"
                        @size-change="sizeChange2"
                        :total="total2">
                </el-pagination>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PerMv",
        data(){
            return{
                title:'',
                page:1,
                size:10,
                total:0,
                allDeps:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                popVisible:false,
                inputDepName:'',
                joblevels:[],
                inputName:'',
                rules:{
                    afterdepid: [{required: true, message: '不能为空', trigger: 'blur'}],
                    afterjobid: [{required: true, message: '不能为空', trigger: 'blur'}],
                    reason: [{required: true, message: '不能为空', trigger: 'blur'}],
                    remark: [{required: true, message: '不能为空', trigger: 'blur'}],
                },
                keywords:'',
                empMove:[],
                empMoves:{
                    eid:'',
                    afterdepid:'',
                    afterjobid:'',
                    reason:'',
                    remark:''
                },
                beforeDep:'',
                beforeJob:'',
                dialogVisible: false,
                total2:0,
                page2:1,
                size2:10,
                search:'',
                dialogVisible2:false,
                emp:[]
            }
        },
        methods:{
            currentChange(currentChange){
                this.page=currentChange
                this.initEmpMove()
            },
            sizeChange(sizeChange){
                this.size=sizeChange
                this.initEmpMove()
            },
            showDeptView(){
                this.popVisible = !this.popVisible
            },
            handleNodeClick(data){
                this.popVisible=false
                this.inputDepName=data.name
                this.empMoves.afterdepid=data.name
                this.empMoves.afterdepid=data.name

            },
            initData(){
                this.getRequest("/emp/basic/jobLevels/").then(res=>{
                    if(res) {
                        this.joblevels = res
                    }
                this.getRequest("/system/basic/department/").then(resp => {
                    if (resp) {
                        this.allDeps = resp;
                    }
                })
                })
            },
            choose(data){
                this.inputName=data.name;
                this.empMoves.eid=data.id;
                this.beforeDep=data.dname;
                this.beforeJob=data.jname
                this.dialogVisible2=false
            },
            currentChange2(currentChange2){
                this.page2=currentChange2
                this.initEmp()
            },
            sizeChange2(sizeChange2){
                this.size2=sizeChange2
                this.initEmp()
            },
            showEmp(){
                this.dialogVisible2=true
                this.initEmp()
            },
            initEmp(){
                this.getRequest("/personnel/train/emp?page="+this.page2+"&size="+this.size2).then(res=>{
                    if(res){
                        this.emp=res.data
                        this.total2=res.total
                    }
                })
            },
            empty(){
                       this.empMoves.eid=''
                       this.empMoves.afterdepid=''
                       this.empMoves.afterjobid=''
                       this.empMoves.reason=''
                       this.empMoves.remark=''
                       this.inputDepName=''
                       this.inputName=''
                       this.beforeDep=''
                       this.beforeJob=''
            },
            deleteEmpMove(data){
                this.$confirm('此操作将永久删除【' + data.employee.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/personnel/remove/" + data.id).then(res => {
                        if (res) {
                            this.initEmpMove()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            showEditView(data){
                this.title='更新员工调动'
                this.initData()
                this.inputName=data.employee.name
                this.beforeDep=data.department.name
                this.beforeJob=data.jobLevel.name
                this.inputDepName=data.afterdepid

                this.empMoves=data
                console.log(data)

                this.dialogVisible=true
            },
            initEmpMove(){
                this.getRequest("/personnel/remove/?page="+this.page+"&size="+this.size+"&keywords="+this.keywords).then(res=>{
                    if(res){
                        this.empMove=res.data
                        this.total=res.total
                    }
                })
            },
            addEmpMove(){
                this.$refs['empForm'].validate(valid => {
                    if (valid) {
                        if(this.empMoves.id){
                            this.putRequest("/personnel/remove/",this.empMoves).then(res=>{
                                if(res){
                                    this. dialogVisible=false
                                    this.initEmpMove()
                                    this.empty()
                                }
                            })
                        }else{
                            if(this.inputName){
                                this.postRequest("/personnel/remove/",this.empMoves).then(res=>{
                                    if(res){
                                        this. dialogVisible=false
                                        this.initEmpMove()
                                        this.empty()
                                    }
                                })
                            }
                        }


                    }

                });
            },
            showAddView(){
                this.title='添加员工调动'
                this. dialogVisible=true
                this.initData()
            }
        },
        mounted(){
            this.initEmpMove()
        }
    }
</script>

<style scoped>

</style>
