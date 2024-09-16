import {Action, getModule, Module, Mutation, VuexModule} from 'vuex-module-decorators';
import {dictionaryAll, ISystemDictionary} from '@/api/systems';
// import { getToken, setToken, removeToken } from '@/utils/cookies';
import store from '@/store';

export interface ISystemState {
    dicts: ISystemDictionary[];
}

@Module({dynamic: true, store, name: 'system'})
class System extends VuexModule implements ISystemState {
    public dicts: ISystemDictionary[] = [];

    @Action
    public async getDicts() {
        let dicts: ISystemDictionary[] = await dictionaryAll();
        this.SET_DICTS(dicts);
    }

    @Mutation
    private SET_DICTS(dicts: ISystemDictionary[]) {
        this.dicts = dicts;
    }
}

export const SystemModule = getModule(System);
