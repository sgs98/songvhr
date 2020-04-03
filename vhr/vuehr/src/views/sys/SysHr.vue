<template>
    <div>
        <div style="margin-top: 10px;display: flex;justify-content: center">
            <el-input v-model="keywords" placeholder="通过用户名搜索用户..." prefix-icon="el-icon-search"
                      style="width: 400px;margin-right: 10px" @keydown.enter.native="doSearch"></el-input>
            <el-button icon="el-icon-search" type="primary" @click="doSearch">搜索</el-button>
        </div>
        <div class="hr-container">
            <el-card class="hr-card" v-for="(h,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{h.name}}</span>
                    <el-button style="float: right; padding: 3px 0; color: red" type="text" icon="el-icon-delete" @click="deleteHr(h.id,h.name)"></el-button>
                </div>
                <div>
                    <div class="img-container">
                        <img :src="h.userface" :alt="h.name" :title="h.name" class="userface-img"/>
                    </div>
                    <div class="userinfo-container">
                        <div>用户名:{{h.name}}</div>
                        <div>手机号码:{{h.phone}}</div>
                        <div>电话号码:{{h.telephone}}</div>
                        <div>用户状态:
                            <el-switch
                                    v-model="h.enabled"
                                    active-text="启用"
                                    @change="enabledChange(h)"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                    inactive-text="禁用">
                            </el-switch>
                        </div>
                        <div>用户角色:
                          <el-tag type="success" style="margin-right: 4px" v-for="(role,indexj) in h.roles" :key="indexj">{{role.namezh}}</el-tag>
                            <el-popover
                                    placement="bottom"
                                    title="角色列表"
                                    width="200"
                                    @hide="hidePop(h)"
                                    @show="showPop(h)"
                                    trigger="click">
                                <el-select multiple v-model="selectedRoles" placeholder="请选择">
                                    <el-option :key="indexk"
                                            v-for="(r,indexk) in allRoles"
                                            :label="r.namezh"
                                            :value="r.id">
                                    </el-option>
                                </el-select>
                                <el-button slot="reference" type="text" icon="el-icon-more"></el-button>
                            </el-popover>
                        </div>
                        <div>备注:{{h.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data(){
            return{
                keywords:'',
                hrs:[],
                allRoles:[],
                selectedRoles:[]
            }
        },
        methods:{
            deleteHr(id,name){
                this.$confirm('此操作将永久删除【' + name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/hr/"+id).then(res=>{
                        if(res){
                            this.initHrs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            doSearch(){
                this.getRequest("/system/hr/?keywords="+this.keywords).then(res=>{
                    if(res){
                        this.hrs=res
                    }
                })
            },
            hidePop(hr){
                let roles = [];
                Object.assign(roles, hr.roles);
                let flag = false;
                if (roles.length != this.selectedRoles.length) {
                    flag = true;
                } else {
                    for (let i = 0; i < roles.length; i++) {
                        let role = roles[i];
                        for (let j = 0; j < this.selectedRoles.length; j++) {
                            let sr = this.selectedRoles[j];
                            if (role.id == sr) {
                                roles.splice(i, 1);
                                i--;
                                break;
                            }
                        }
                    }
                    if (roles.length != 0) {
                        flag = true;
                    }
                }
                if (flag) {
                    let url = '/system/hr/updateHrRole?hrid=' + hr.id;
                    this.selectedRoles.forEach(sr => {
                        url += '&rids=' + sr;
                    });
                    this.putRequest(url).then(resp => {
                        if (resp) {
                            this.initHrs();
                        }
                    });
                }

            },
            initHrs(){
                this.getRequest("/system/hr/").then(res=>{
                    if(res){
                       this.hrs=res
                    }
                })
            },
            initRoles(){
                this.getRequest("/system/hr/roles").then(res=>{
                    if(res){
                        this.allRoles=res
                    }
                })
            },
            showPop(h){
                this.initRoles()

                let roles=h.roles
                this.selectedRoles=[]
                roles.forEach(r=>{
                    this.selectedRoles.push(r.id)
                })


            },
            enabledChange(h){
                delete h.roles
                 this.putRequest("/system/hr/",h).then(res=>{
                     if(res){
                         this.initHrs()
                     }
                 })
            }
        },
        mounted(){
            this.initHrs()
        }
    }
</script>

<style>
    .userinfo-container div{
        font-size: 12px;
        color: #409eff;
    }
    .userinfo-container{
        margin-top: 20px;
    }
    .img-container{
        width: 100%;
        display: flex;
        justify-content: center;
    }
    .hr-card{
        width: 360px;
        margin-bottom:20px ;
    }
    .hr-container{
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }
    .userface-img{
        width: 72px;
        height: 72px;
        border-radius: 42px;
    }
</style>
