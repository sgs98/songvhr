<template>
<div>
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
                prop="salarys"
                label="薪资"
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
                <el-button type="primary"  @click="emitToParent(scope.row)" style="padding: 3px">选择</el-button>
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
</div>
</template>

<script>
    export default {
        name: "PerEmp",
        data(){
            return{
                emp:[],
                page2:1,
                size2:10,
                total2:0,
                search:''
            }
        },

        methods:{
            initEmp(){
                this.getRequest("/personnel/train/emp?page="+this.page2+"&size="+this.size2).then(res=>{
                    if(res){
                        this.emp=res.data
                        this.total2=res.total
                    }
                })
            },
            emitToParent(data){
             this.$emit("child-even",data)
            },
            currentChange2(currentChange2){
                this.page2=currentChange2
                this.initEmp()
            },
            sizeChange2(sizeChange2){
                this.size2=sizeChange2
                this.initEmp()
            },
        },
        mounted(){
            this.initEmp()
        }
    }
</script>

<style scoped>

</style>
