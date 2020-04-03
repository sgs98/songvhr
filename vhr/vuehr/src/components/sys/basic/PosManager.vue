<template>
    <div>
        <div>
            <el-input
                    class="addPosInput"
                    size="small"
                    placeholder="添加职位..."
                    prefix-icon="el-icon-plus"
                    v-model="pos.name"
                    @keydown.enter.native="addPosition"
            >
            </el-input>
            <el-button size="small" type="primary" @click="addPosition" icon="el-icon-plus">添加</el-button>
        </div>
        <div class="posManageMain">
            <el-table
                    :data="positions"
                    border
                    stripe
                    size="small"
                    @selection-change="handleSelectionChange"
                    style="width: 70%">
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
                        label="职位名称"
                        width="200">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="180">
                </el-table-column>
                <el-table-column
                        label="是否启用">
                    <template slot-scope="scope">
                        <el-tag type="success" size="small" v-if="scope.row.enabled">已启用</el-tag>
                        <el-tag type="danger" size="small" v-else>未启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column
                        label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showEditView(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="deletePosition(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button @click="deleteMany" type="danger" size="small" style="margin-top: 8px" :disabled="multipleSelection.length==0">批量删除</el-button>
        </div>
        <el-dialog
                title="修改职位"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
             <el-tag>职位名称</el-tag>
             <el-input class="updatePosInput" v-model="updatePos.name" size="small"></el-input>
            </div>
            <dvi>
                <el-tag>是否启用</el-tag>
                <el-switch style="margin-left: 8px"
                           v-model="updatePos.enabled"
                           active-text="启用"
                           inactive-text="禁用">
                </el-switch>
            </dvi>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible = false">取 消</el-button>
                <el-button size="small" type="primary" @click="updatePosition">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PosManager",
        data() {
            return {
                pos: {
                    name: ''
                },
                positions: [],
                dialogVisible:false,
                updatePos: {
                    name: '',
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
                    this.deleteRequest("/system/basic/pos/" + ids).then(res => {
                        if (res) {
                            this.initPositions();
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
            initPositions() {
                this.getRequest("/system/basic/pos/").then(res => {
                    if (res) {
                        this.positions = res
                    }

                })
            },
            addPosition() {
                if (this.pos.name) {
                    this.postRequest("/system/basic/pos/", this.pos).then(res => {
                        this.initPositions()
                        this.pos.name = ''
                    })
                } else {
                    this.$message({
                        message: '职位名称不可以为空',
                        type: 'warning'
                    })
                }
            },
            deletePosition(index, data) {
                this.$confirm('此操作将永久删除【' + data.name + '】职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/" + data.id).then(res => {
                        if (res) {
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            },
            updatePosition() {
                this.putRequest("/system/basic/pos/",this.updatePos).then(res => {
                    if(res){
                        this.initPositions();
                        this.updatePos.name=''
                        this.dialogVisible=false
                    }
                })
            },
            showEditView(index,data){
                Object.assign(this.updatePos,data)
                this.dialogVisible=true
            }
        },
        mounted() {
            this.initPositions()
        }
    }
</script>

<style scoped>
    .addPosInput {
        width: 300px;
        margin-right: 8px
    }

    .posManageMain {
        margin-top: 10px;
    }
    .updatePosInput{
        width: 200px;
        margin-left: 8px;
    }
</style>
