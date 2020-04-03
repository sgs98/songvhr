<template>
    <div>
        <div style="display: flex; justify-content: space-between;">
            <div>
                <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索..."
                          @clear="initEmpSalary"
                          prefix-icon="el-icon-search"
                          style="width: 350px;margin-right: 10px"
                          clearable
                          :clear="initEmpSalary"
                          v-model="keywords"
                          @keydown.enter.native="initEmpSalary"
                >
                </el-input>
                <el-button icon="el-icon-search" type="primary" @click="initEmpSalary" >
                    搜索
                </el-button>
            </div>
            <div>
                <el-button type="primary" icon="el-icon-plus" @click="showAddView">
                    调动员工薪资
                </el-button>
            </div>
        </div>
        <div style="margin-top: 8px">
            <el-table :data="Persalarys" stripe
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
                        prop="employee.phone"
                        label="联系方式"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="employee.idcard"
                        label="身份证"
                        align="center"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="beforesalary"
                        label="调动前薪资"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="aftersalary"
                        label="调动后薪资"
                        align="center"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="asdate"
                        label="调动时间"
                        align="center"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="reason"
                        label="调动原因"
                        align="center"
                        width="300">
                </el-table-column>
                <el-table-column
                        prop="remark"
                        label="备注"
                        align="center"
                        width="300">
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditView(scope.row)" style="padding: 3px">编辑</el-button>
                        <el-button @click="deletePersalary(scope.row)" style="padding: 3px" type="danger">删除
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
        <el-dialog :title="title"
                :visible.sync="dialogVisible"
                width="60%" height="50%">
            <div>
                <el-form :rules="rules" :model="Persalary" ref="empForm">
                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="姓名:" prop="name">

                                <div style="display: inline-flex;">
                                    <el-input size="mini" style="width: 200px; display: none" prefix-icon="el-icon-edit" v-model="Persalary.eid"
                                              placeholder="请输入员工姓名"></el-input>
                                    <el-input size="mini" style="width: 250px" prefix-icon="el-icon-edit"
                                              v-model="inputName" readonly placeholder="请输入员工姓名"></el-input>
                                    <el-button type="primary" style="margin-left: 8px" size="mini" @click="showEmp">选择员工</el-button>
                                </div>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="调动前薪资:" prop="beforesalary">
                                <el-input size="mini" style="width: 295px" readonly prefix-icon="el-icon-edit" v-model="Persalary.beforesalary"
                                          placeholder="请输入调动前薪资"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="调动后薪资:" prop="aftersalary">
                                <el-input size="mini" style="width: 200px" prefix-icon="el-icon-edit" v-model="Persalary.aftersalary"
                                          placeholder="请输入调动后薪资"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="12">
                            <el-form-item label="调动原因:" prop="reason">
                                <el-input size="mini" style="width: 300px" prefix-icon="el-icon-edit" v-model="Persalary.reason"
                                          placeholder="请输入调动原因"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>

                        <el-col :span="15">
                            <el-form-item label="备注:" prop="remark">
                                <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit" v-model="Persalary.remark"
                                          placeholder="请输入备注"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="AddPersalary">确 定</el-button>
        </el-dialog>
        <el-dialog
                :visible.sync="dialogVisible2"
                width="60%" height="50%">
            <PerEmp @child-even="parentEven"></PerEmp>
        </el-dialog>
    </div>
</template>

<script>
    import PerEmp from '../../components/per/PerEmp'
    export default {
        name: "Persalary",
        data(){
            return{
                title:'',
                dialogVisible2:false,
                inputName:'',
                rules:{
                    aftersalary: [{required: true, message: '不能为空', trigger: 'blur'}],
                    reason: [{required: true, message: '不能为空', trigger: 'blur'}],
                    remark: [{required: true, message: '不能为空', trigger: 'blur'}],
                },
                keywords:'',
                Persalarys:[],
                total:0,
                size:10,
                page:1,
                dialogVisible:false,
                Persalary:{
                    eid:'',
                    beforesalary:'',
                    aftersalary:'',
                    reason:'',
                    remark:''
                }
            }
        },

        components:{
            PerEmp
        },
        methods:{
            empty(){
                    this.Persalary.eid=''
                    this.Persalary.beforesalary=''
                    this.Persalary.aftersalary=''
                    this.Persalary.reason=''
                    this.Persalary.remark=''
                    this.inputName=''
                    this.Persalary.id=''
            },
            AddPersalary(){
                this.$refs['empForm'].validate(valid => {

                if (valid) {
                    if (this.Persalary.id) {
                        this.putRequest("/personnel/salary/", this.Persalary).then(res => {
                            if (res) {
                                this.dialogVisible = false
                                this.initEmpSalary()
                                this.empty()
                            }
                        })
                    } else {
                        if (this.inputName) {
                            this.postRequest("/personnel/salary/", this.Persalary).then(res => {
                                if (res) {
                                    this.dialogVisible = false
                                    this.initEmpSalary()
                                    this.empty()
                                }
                            })
                        }
                    }
                }
                })
            },
            parentEven(data){

                if(data.salarys==null){
                    this.Persalary.beforesalary='0'
                    this.dialogVisible2=false
                    this.Persalary.eid=data.id
                    this.inputName=data.name
                }else{
                this.Persalary.eid=data.id
                this.inputName=data.name
                this.Persalary.beforesalary=data.salarys
                this.dialogVisible2=false
                }
            },
            showEmp(){
                this.dialogVisible2=true
            },
            showAddView(){
                this.title='添加员工薪资'
                this.dialogVisible=true
                this.empty()
            },
            initEmpSalary(){
                this.getRequest("/personnel/salary/?page="+this.page+"&size="+this.size+"&keywords="+this.keywords).then(res=>{
                    if(res){
                        this.Persalarys=res.data
                        this.total=res.total
                    }
                })
            },
            currentChange(currentChange){
                this.page=currentChange
                this.initEmpSalary()
            },
            sizeChange(sizeChange){
                this.size=sizeChange
                this.initEmpSalary()
            },
            showEditView(data){
                this.inputName=data.employee.name
                this.title='编辑员工薪资'
                this.dialogVisible=true
                this.Persalary=data
                this.Persalary.beforesalary=data.aftersalary
            },
            deletePersalary(data){
                this.$confirm('此操作将永久删除【' + data.employee.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/personnel/salary/" + data.id).then(res => {
                        if (res) {
                            this.initEmpSalary()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }

        },
        mounted(){
            this.initEmpSalary()
        }
    }
</script>

<style scoped>

</style>
