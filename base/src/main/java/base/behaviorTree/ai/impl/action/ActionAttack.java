package base.behaviorTree.ai.impl.action;

import base.behaviorTree.ai.abs.BaseAction;
import base.behaviorTree.ai.common.EStatus;
import base.behaviorTree.ai.ifs.IBehaviour;

public class ActionAttack extends BaseAction {

    @Override
    public EStatus update() {
        System.out.println("ActionAttack 攻击");
        return EStatus.Success;
    }

    @Override
    public void addChild(IBehaviour child) {
    }
}