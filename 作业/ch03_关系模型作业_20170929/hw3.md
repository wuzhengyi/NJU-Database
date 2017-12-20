1. $\prod_{sno}(\sigma_{cno=c001}(T))$
2. $\prod_{sno,sn}((\prod_{sno}(\sigma_{dept=CS}(C \bowtie T)))\bowtie S)$
3. 取得选修过体育课的同学学号
    $R = \prod_{sno}(\sigma_{cn=PE}(C \bowtie T))$    

    查询选修过体育课的同学的姓名和院系
    $\prod_{sn,dept}(R \bowtie  S)$

    查询没有选修过体育课的同学的姓名和院系
    $\prod_{sn,dept}(S)-\prod_{sn,dept}(R \bowtie  S)$   

    代入得
    $\prod_{sn,dept}(S)-\prod_{sn,dept}((\prod_{sno}(\sigma_{cn=PE}(C \bowtie T))) \bowtie  S)$
4. 取得所有学生的学号和姓名
    $\prod_{sno,sn}(S)$   

    取得每个课程对应的课程号，学号，姓名，院系
    $R = \prod_{cno,sno,dept,sn}(T \bowtie S)$   

    取得选修过非本院系课程的学生的学号和名字

    $\prod_{sno,sn}(\sigma_{S.dept \neq C.dept}(R \bowtie_{T.cno=S.cno} C))$   

    得到之选修过学生自己所在院系开色号的课程的学号和姓名

    $\prod_{sno,sn}(S) - \prod_{sno,sn}(\sigma_{S.dept \neq C.dept}(R \bowtie_{T.cno=C.cno} C))$
    
    $= \prod_{sno,sn}(S) - \prod_{sno,sn}(\sigma_{S.dept \neq C.dept}((\prod_{cnp,sno,dept,sn}(T \bowtie S)) \bowtie_{T.cno=S.cno} C))$
5. 取得c009的所有前导课程号
    $R = \prod_{pno}(\sigma_{cno = c009}(P))$

    得到选修了所有前导课程的同学学号
    $\prod_{sno,cno}(T) \div R$   (其中令pno等于cno)   

    最后得到这些同学的学号和姓名
    $\prod_{sno,sn}(S \bowtie (\prod_{sno,cno}(T) \div R))$
    
    $= \prod_{sno,sn}(S \bowtie (\prod_{sno,cno}(T) \div (\prod_{pno}(\sigma_{cno = c009}(P)))))$

6. 所有成绩都及格的同学的学号
    $R = \prod_{sno}(T) - \prod_{sno}(\sigma_{g < 60}(R))$   

    计算机系所有课程的课程号
    $\prod_{cno}(\sigma_{dept = CS}(C))$   

    选修过计算机系所有课的同学的学号
    $W = \prod_{sno,cno}(T) \div \prod_{cno}(\sigma_{dept = CS}(C))$

    所有成绩都及格（成绩≥60），且选修过‘计算机’系开设的所有课程的学生的学号

    $R - W = (\prod_{sno}(T) - \prod_{sno}(\sigma_{g < 60}(R))) - (\prod_{sno,cno}(T) \div \prod_{cno}(\sigma_{dept = CS}(C)))$
7. 投影所有课程号的所有分数
    $R_1 = \prod_{cno,g}(T)$   

    查询各课非最高分的课程号对应的分数：令S = T

    $R_2 = \prod{cno,g}(\sigma_{T.g<S.g \wedge T.cno = S.cno}(T \times S))$   

    最后得到各课称号与他对应的最高分分数

    $MAX = R_1 - R_2 = \prod_{cno,g}(T) - \prod{cno,g}(\sigma_{T.g<S.g \wedge T.cno = S.cno}(T \times S))$

    同理得到各课称号与他对应的最低分分数

    $MIN = \prod_{cno,g}(T) - \prod{cno,g}(\sigma_{T.g>S.g \wedge T.cno = S.cno}(T \times S))$

    最后合并一下
    $MAX \bowtie_{MAX.cno = MIN.cno} MIN$
    
    $= \prod_{cno,g}(T) - \prod{cno,g}(\sigma_{T.g<S.g \wedge T.cno = S.cno}(T \times S)) \bowtie_{MAX.cno = MIN.cno} \prod_{cno,g}(T) - \prod{cno,g}(\sigma_{T.g>S.g \wedge T.cno = S.cno}(T \times S))$

