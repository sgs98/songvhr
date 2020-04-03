<template>
    <div>
        <div>
            <el-table :data="emps" border stripe width="70%" size="mini">
                <el-table-column type="selection" align="left" width="55"></el-table-column>
                <el-table-column prop="name" label="姓名"  width="120" align="left"></el-table-column>
                <el-table-column prop="workid" label="工号"  width="120"></el-table-column>
                <el-table-column prop="email" label="电子邮件"  width="200"></el-table-column>
                <el-table-column prop="phone" label="电话号码"  width="120"></el-table-column>
                <el-table-column label="所属部门" align="center">
                    <template slot-scope="scope">
                        <el-tooltip placement="right" v-if="scope.row.salary!=null">
                            <div slot="content">
                                <table>
                                    <tr>
                                        <td>基本工资</td>
                                        <td>{{scope.row.salary.basicSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>交通补助</td>
                                        <td>{{scope.row.salary.trafficSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>午餐补助 </td>
                                        <td>{{scope.row.salary.lunchSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>奖金</td>
                                        <td>{{scope.row.salary.bonus}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金比例</td>
                                        <td>{{scope.row.salary.pensionPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金基数</td>
                                        <td>{{scope.row.salary.pensionBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险比例</td>
                                        <td>{{scope.row.salary.medicalPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险基数</td>
                                        <td>{{scope.row.salary.medicalBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金比例</td>
                                        <td>{{scope.row.salary.accumulationFundPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金基数</td>
                                        <td>{{scope.row.salary.accumulationFundBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>启用时间</td>
                                        <td>{{scope.row.salary.createDate}}</td>
                                    </tr>
                                </table>
                            </div>
                            <el-tag>{{scope.row.salary.name}}</el-tag>
                        </el-tooltip>
                        <el-tag v-else>暂未设置</el-tag>

                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-popover
                                placement="left"
                                title="修改工资账套"
                                width="200"
                                @hide="hidePop(scope.row)"
                                @show="showPop(scope.row.salary)"
                                trigger="click">
                            <div>
                                <el-select v-model="currentSalary" placeholder="请选择" size="mini">
                                    <el-option
                                            v-for="item in salary"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </div>
                            <el-button slot="reference" type="danger">修改工资账套</el-button>
                        </el-popover>

                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
            <el-pagination
                    background
                    layout="sizes, prev, pager, next, jumper, ->, total, slot"
                    @current-change="currentChange"
                    @size-change="sizeChange"
                    :total="total">
            </el-pagination>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "SalSobCfg",
        data(){
            return{
                emps:[],
                salary:[],
                currentSalary:null,
                total:0,
                page:1,
                size:10,
            }
        },
        methods:{
            currentChange(currentChange){
                this.page=currentChange
                this.initEmps()
            },
            sizeChange(sizeChange){
                this.size=sizeChange
                this.initEmps()
            },
            hidePop(data){
                if (this.currentSalary) {
                    this.putRequest('/salary/sobcfg/?eid=' + data.id + '&sid=' + this.currentSalary).then(resp => {
                        if (resp) {
                            this.initEmps()
                        }
                    });
                }

            },
            showPop(data){
                if(data){
                    this.currentSalary=data.id
                }else{
                    this.currentSalary=null
                }

            },
           initEmps(){
               this.getRequest("/salary/sobcfg/?page="+this.page+"&size="+this.size).then(res=>{
                   if(res){
                       this.emps=res.data
                       this.total=res.total
                   }
               })
           },
            initSalaries(){
                this.getRequest("/salary/sobcfg/salaries").then(res=>{
                    if(res){
                        this.salary=res
                    }
                })
            }
        },
        mounted(){
            this.initEmps()
            this.initSalaries()
        }
    }
</script>

<style scoped>

</style>
