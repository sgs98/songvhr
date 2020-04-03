<template>
    <div>
        <div>
            <el-input
                    class="addJobInput"
                    size="small"
                    placeholder="添加职称..."
                    prefix-icon="el-icon-plus"
                    v-model="job.name"
                    @keydown.enter.native="addJobLevel"
            >
            </el-input>
            <el-select v-model="job.titlelevel" size="small" placeholder="职称等级">
                <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
            <el-button size="small" type="primary" @click="addJobLevel" icon="el-icon-plus">添加</el-button>
        </div>
        <div class="jobManageMain">
            <el-table
                    :data="JobLevels"
                    border
                    stripe
                    size="small"
                    @selection-change="handleSelectionChange"
                    style="width: 80%">
                <el-table-column
                        type="selection"
                        width="55"
                ></el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="titlelevel"
                        label="等级"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="createdate"
                        label="创建时间"
                        width="180">
                </el-table-column>
                <el-table-column
                        label="是否启用">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag type="danger" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作"
                        width="200">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showEditView(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="deleteJobLevel(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="deleteMany" type="danger" size="small" style="margin-top: 8px" :disabled="multipleSelection.length==0">批量删除</el-button>
        </div>
        <el-dialog
                title="修改职称"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td><el-tag>职称名称</el-tag></td>
                        <td><el-input class="updateJobInput" v-model="updateJob.name" size="small"></el-input></td>
                    </tr>
                    <tr>
                        <td><el-tag>职称等级</el-tag></td>
                        <td>
                            <el-select v-model="updateJob.titlelevel" style="margin-left: 8px" size="small" placeholder="职称等级">
                                <el-option
                                        v-for="item in options"
                                        :key="item.value"
                                        :label="item.label"
                                        :value="item.value">
                                </el-option>
                            </el-select>
                        </td>
                    </tr>
                    <tr>
                        <td><el-tag>是否启用</el-tag></td>
                        <td>
                            <el-switch style="margin-left: 8px"
                                       v-model="updateJob.enabled"
                                       active-text="启用"
                                       inactive-text="禁用">
                            </el-switch>
                        </td>
                    </tr>
                </table>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="updateJobLevel">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "JobManager",
        data() {
            return {
                options: [{
                    value: '正高级',
                    label: '正高级'
                }, {
                    value: '副高级',
                    label: '副高级'
                }, {
                    value: '中级',
                    label: '中级'
                }, {
                    value: '初级',
                    label: '初级'
                }, {
                    value: '员级',
                    label: '员级'
                }],
                value: '',
                job: {
                    name: '',
                    titlelevel:''
                },
                JobLevels: [],
                dialogVisible:false,
                updateJob: {
                    name: '',
                    titlelevel:'',
                    enabled:false
                },
                multipleSelection:[]
            }
        },
        methods: {
            deleteMany(){
                this.$confirm('此操作将永久删除【' + this.multipleSelection.length + '】条数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids='?'
                    this.multipleSelection.forEach(item=>{
                        ids+="ids="+item.id+"&"
                    })
                    this.deleteRequest("/system/basic/job/" + ids).then(res => {
                        if (res) {
                            this.initJobLevels();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            initJobLevels() {
                this.getRequest("/system/basic/job/").then(res => {
                    if (res) {
                        this.JobLevels = res
                    }

                })
            },
            addJobLevel() {
                if (this.job.name&&this.job.titlelevel) {
                    this.postRequest("/system/basic/job/", this.job).then(res => {
                        this.initJobLevels()
                        this.job.name = ''
                        this.job.titlelevel = ''
                    })
                } else {
                    this.$message({
                        message: '职称名称与等级不可以为空',
                        type: 'warning'
                    })
                }
            },
            deleteJobLevel(index, data) {
                this.$confirm('此操作将永久删除【' + data.name + '】职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/job/" + data.id).then(res => {
                        if (res) {
                            this.initJobLevels();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            updateJobLevel() {
                this.putRequest("/system/basic/job/",this.updateJob).then(res => {
                    if(res){
                        this.initJobLevels();
                        this.updateJob.name=''
                        this.dialogVisible=false
                    }
                })
            },
            showEditView(index,data){
                Object.assign(this.updateJob,data)
                this.dialogVisible=true
            }
        },
        mounted() {
            this.initJobLevels()
        }
    }
</script>

<style scoped>
    .addJobInput {
        width: 300px;
        margin-right: 8px
    }

    .jobManageMain {
        margin-top: 10px;
    }
    .updateJobInput{
        width: 203px;
        margin-left: 8px;
    }
</style>
