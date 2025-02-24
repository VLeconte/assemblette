import type { Deputy } from '@/entities/deputy'
import _ from 'lodash'
import type { Vote } from '@/entities/vote'

export default class VotesUtils {
  public static getDeputiesVotesNumberForVoteState(
    deputies: Deputy[],
    votesByDeputyId: _.Dictionary<Vote[]>,
    voteState: string,
  ) {
    return _.filter(deputies, (deputy) => {
      return (
        _.includes(_.keys(votesByDeputyId), deputy.id) &&
        votesByDeputyId[deputy.id][0].state === voteState
      )
    }).length
  }
}
