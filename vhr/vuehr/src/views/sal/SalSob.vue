<template>
    <div>
        <div style="display: flex;justify-content: space-between">
            <el-button type="primary" icon="el-icon-plus" @click="showAddSalaryView">添加工资账套</el-button>
            <el-button type="success" icon="el-icon-refresh" @click="initSalaries"></el-button>
        </div>
        <div style="margin-top: 10px">
          <el-table :data="salaries" border stripe size="mini">
             <el-table-column type="selection" width="55"></el-table-column>
             <el-table-column width="120" prop="name" label="账套名称"></el-table-column>
             <el-table-column width="70" prop="basicSalary" label="基本工资"></el-table-column>
             <el-table-column width="70" prop="trafficSalary" label="交通补助"></el-table-column>
             <el-table-column width="70" prop="lunchSalary" label="午餐补助"></el-table-column>
             <el-table-column width="70" prop="bonus" label="奖金"></el-table-column>
             <el-table-column width="100" prop="createDate" label="启动时间"></el-table-column>
             <el-table-column label="养老金" align="center">
                 <el-table-column width="70" prop="pensionPer" label="比率"></el-table-column>
                 <el-table-column width="70" prop="pensionBase" label="基数"></el-table-column>
             </el-table-column>
              <el-table-column label="医疗保险" align="center">
                  <el-table-column width="70" prop="medicalPer" label="比率"></el-table-column>
                  <el-table-column width="70" prop="medicalBase" label="基数"></el-table-column>
              </el-table-column>
              <el-table-column label="公积金" align="center">
                  <el-table-column width="70" prop="accumulationFundPer" label="比率"></el-table-column>
                  <el-table-column width="70" prop="accumulationFundBase" label="基数"></el-table-column>
              </el-table-column>
              <el-table-column label="操作">
                  <template slot-scope="scope">
                      <el-button @click="showUpdateSalaryView(scope.row)">编辑</el-button>
                      <el-button type="danger" @click="deleteSalary(scope.row)">删除</el-button>
                  </template>
              </el-table-column>
          </el-table>
        </div>
        <el-dialog
                :title="dialogTitle"
                :visible.sync="dialogVisible"
                width="50%">
            <div style="display: flex;justify-content: space-around;align-items: center">
                <el-steps direction="vertical" :active="activeItemIndex">
                    <el-step :title="itemName" v-for="(itemName,index) in salaryItemName" :key="index"></el-step>
                </el-steps>
                <el-input v-model="salary[title]" style="width: 200px" v-show="activeItemIndex==index" :placeholder="'请输入'+salaryItemName[index]+'...'" v-for="(value,title,index) in salary" :key="index"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="preStep">{{activeItemIndex==10?'取消':'上一步'}}</el-button>
                <el-button type="primary" @click="nextStep">{{activeItemIndex==10?'完成':'下一步'}}</el-button>
              </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "SalSob",
        data(){
            return{
                dialogTitle:'添加工资账套',
                activeItemIndex:0,
                salaries:[],
                dialogVisible:false,
                salaryItemName:[
                    '基本工资',
                    '交通补助',
                    '午餐补助',
                    '奖金',
                    '养老金比率',
                    '养老金基数',
                    '医疗保险比率',
                    '医疗保险基数',
                    '公积金比率',
                    '公积金基数',
                    '账套名称'
                ],
                salary:{
                    basicSalary:0,
                    trafficSalary:0,
                    lunchSalary:0,
                    bonus:0,
                    pensionPer:0,
                    pensionBase:0,
                    medicalPer:0,
                    medicalBase:0,
                    accumulationFundPer:0,
                    accumulationFundBase:0,
                    name:''
                }
            }
        },
        methods:{
            deleteSalary(data){
                this.$confirm('此操作将永久删除【' + data.name + '】职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/salary/sob/"+data.id).then(res=>{
                        if(res){
                            this.initSalaries()
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            preStep(){
                if(this.activeItemIndex==0){
                    return
                }else if(this.activeItemIndex==10){

                    //关闭对话框
                    this.dialogVisible=false
                    return
                }
                this.activeItemIndex--
            },
            nextStep(){

                if(this.activeItemIndex==10){
                    if(this.salary.id){
                      this.putRequest("/salary/sob/",this.salary).then(res=>{
                          if(res){
                              this.initSalaries()
                              this.dialogVisible=false
                              this.activeItemIndex=0
                          }
                      })
                    }else{
                    this.postRequest("/salary/sob/",this.salary).then(res=>{
                        if(res){
                            this.initSalaries()
                            this.dialogVisible=false
                            this.activeItemIndex=0
                            this.salary={
                                basicSalary:0,
                                trafficSalary:0,
                                lunchSalary:0,
                                bonus:0,
                                pensionPer:0,
                                pensionBase:0,
                                medicalPer:0,
                                medicalBase:0,
                                accumulationFundPer:0,
                                accumulationFundBase:0
                            }
                        }
                    })
                    }
                    return
                }
                this.activeItemIndex++
            },
            initSalaries(){
                this.getRequest("/salary/sob/").then(res=>{
                    if(res){
                        this.salaries=res
                    }
                })
            },
            showUpdateSalaryView(data){

                this.dialogVisible=true
                this.activeItemIndex=0
                this.dialogTitle="编辑工资账套"

                    this.salary.basicSalary=data.basicSalary
                    this.salary.trafficSalary=data.trafficSalary
                    this.salary.lunchSalary=data.lunchSalary
                    this.salary.bonus=data.bonus
                    this.salary.pensionPer=data.pensionPer
                    this.salary.pensionBase=data.pensionBase
                    this.salary.medicalPer=data.medicalPer
                    this.salary.medicalBase=data.medicalBase
                    this.salary.accumulationFundPer=data.accumulationFundPer
                    this.salary.accumulationFundBase=data.accumulationFundBase
                    this.salary.name=data.name
                    this.salary.id=data.id
            },
            showAddSalaryView(){
                //数据初始化
                this.salary={
                    basicSalary:0,
                    trafficSalary:0,
                    lunchSalary:0,
                    bonus:0,
                    pensionPer:0,
                    pensionBase:0,
                    medicalPer:0,
                    medicalBase:0,
                    accumulationFundPer:0,
                    accumulationFundBase:0
                }
                this.dialogVisible=true
                this.activeItemIndex=0
                this.dialogTitle="添加工资账套"
            }
        },
        mounted(){
            this.initSalaries()
        }
    }
</script>

<style scoped>

</style>
