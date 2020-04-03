<template>
    <div>
        <div style="display: flex; justify-content: space-between;">
            <div>
                <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..."
                          @clear="initPerTrain"
                          prefix-icon="el-icon-search"
                          style="width: 350px;margin-right: 10px"
                          clearable
                          :clear="initPerTrain"
                          v-model="keywords"
                          @keydown.enter.native="initPerTrain"
                >
                </el-input>
                <el-button icon="el-icon-search" type="primary" @click="initPerTrain" >
                    搜索
                </el-button>
            </div>
            <div>
                <el-button type="primary" icon="el-icon-plus" @click="showAddView">
                    添加用户
                </el-button>
            </div>
        </div>
        <div style="margin-top: 8px">
        <el-table :data="perTrain"
                  stripe
                  border>
          <el-table-column
               prop="employee.name"
               fixed
               align="left"
               label="姓名"
               width="90">
          </el-table-column>
          <el-table-column
               prop="employee.gender"
               label="性别"
               align="center"
               width="85">
          </el-table-column>
            <el-table-column
                    prop="employee.birthday"
                    label="出生日期"
                    align="center"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="employee.idcard"
                    label="身份证"
                    align="center"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="employee.email"
                    label="电子邮件"
                    align="center"
                    width="200">
            </el-table-column>
            <el-table-column
                    prop="employee.phone"
                    width="100"
                    align="center"
                    label="电话号码">
            </el-table-column>
            <el-table-column
                    prop="employee.address"
                    width="220"
                    align="center"
                    label="联系地址">
            </el-table-column>
            <el-table-column
                    prop="traindate"
                    width="100"
                    align="center"
                    label="培训时间">
            </el-table-column>c
            <el-table-column
                    prop="traincontent"
                    width="150"
                    align="left"
                    label="培训内容">
            </el-table-column>
            <el-table-column
                    prop="remark"
                    width="200"
                    align="left"
                    label="备注">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    width="200"
                    label="操作">
                <template slot-scope="scope">
                    <el-button @click="showEditEmpView(scope.row)" style="padding: 3px">编辑</el-button>
                    <el-button @click="deleteEmpTran(scope.row)" style="padding: 3px" type="danger">删除
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
                width="40%">
            <el-form>
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-form-item label="">
                            <div style="display: inline-flex;">
                            <el-input size="mini" style="width: 300px" prefix-icon="el-icon-edit"
                                          v-model="inputName" readonly placeholder="请输入员工姓名"></el-input>
                            <el-input size="mini" style="width: 300px; display: none" prefix-icon="el-icon-edit"
                                      v-model="pre.eid" readonly placeholder="请输入员工姓名"></el-input>
                            <el-button type="primary" style="margin-left: 8px" size="mini" @click="showEmp">选择员工</el-button>
                            </div>
                        </el-form-item>
                    </el-col>
                </el-row>
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="">
                        <el-tag>培训内容</el-tag>
                        <textarea  style="resize:none;border-radius: 5px;outline: 0" cols="85" rows="9" v-model="pre.traincontent"></textarea>
                    </el-form-item>
                </el-col>
            </el-row>
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="">
                            <el-tag>备注</el-tag>
                            <textarea  style="resize:none;border-radius: 5px;outline: 0" cols="85" rows="9" v-model="pre.remark"></textarea>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-button @click="dialogVisibleClose">取 消</el-button>
                <el-button type="primary" @click="doAddEmpTran">确 定</el-button>
            </el-form>
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
        name: "PerTrain",
        data(){
            return{
                title:'',
                perTrain:[],
                emp:[],
                keywords:'',
                dialogVisible:false,
                dialogVisible2:false,
                pre:{
                    eid:'',
                    traincontent:'',
                    remark:''
                },
                page:1,
                size:10,
                total:0,
                total2:0,
                page2:1,
                size2:10,
                search:'',
                inputName:''
            }
        },
        methods:{
            deleteEmpTran(data){
                this.$confirm('此操作将永久删除【' + data.employee.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/personnel/train/" + data.id).then(res => {
                        if (res) {
                            this.initPerTrain()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            showEditEmpView(data){
                this.title="更新员工培训信息"
                this.dialogVisible=true
                this.inputName=data.employee.name
                this.pre=data
            },
            doAddEmpTran(){
                if(this.pre.eid&&this.pre.traincontent&&this.pre.remark){
                    if(this.pre.id){
                        this.putRequest("/personnel/train/",this.pre).then(res=>{
                            if(res){
                                this.initPerTrain()
                                this.empty()
                                this.dialogVisible=false
                            }
                        })
                    }else{
                        this.postRequest("/personnel/train/",this.pre).then(res=>{
                            if(res){
                                this.initPerTrain()
                                this.empty()
                                this.dialogVisible=false
                            }
                        })
                    }

                }else {
                    this.$message({
                        type: 'info',
                        message: '不能为空'
                    });
                }
            },
            empty(){
                this.pre.eid=''
                this.pre.traincontent=''
                this.pre.remark=''
                this.inputName=''
                this.id=''
            },
            dialogVisibleClose(){
                this.dialogVisible = false

                    this.pre.eid=''
                    this.pre.traincontent=''
                    this.pre.remark=''
                    this.inputName=''

            },
            choose(data){
                this.pre.eid=data.id;
                this.inputName=data.name;
                this.dialogVisible2=false
            },
            currentChange(currentChange){
                this.page=currentChange
                this.initPerTrain()
            },
            sizeChange(sizeChange){
                this.size=sizeChange
                this.initPerTrain()
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
            showAddView(){
                this.empty()
                this.title="添加员工培训信息"
                this.dialogVisible=true
                this.pre.id=''
            },
            initPerTrain(){
                this.getRequest("/personnel/train/?page="+this.page+"&size="+this.size+"&keywords="+this.keywords).then(res=>{
                    if(res){
                        this.perTrain=res.data
                        this.total=res.total
                    }
                })
            },
            initEmp(){
                this.getRequest("/personnel/train/emp?page="+this.page2+"&size="+this.size2).then(res=>{
                    if(res){
                        this.emp=res.data
                        this.total2=res.total
                    }
                })
            }
        },
        mounted(){
            this.initPerTrain()
        }
    }
</script>

<style scoped>

</style>
