import type { Deputy } from '@/entities/deputy'
import type { Mandate } from '@/entities/mandate'
import _ from 'lodash'
import { getActiveMandatesForSpecificDate } from './mandates-utils'

export default class DeputiesUtils {
  public static getActiveDeputiesByPGIdForSpecificDate(
    deputies: Deputy[],
    mandates: Mandate[],
    date: string,
  ) {
    const mandatesByDeputyId = _.groupBy(mandates, (mandate) => mandate.deputy.id)
    const activeDeputies = _.filter(deputies, (deputy) => {
      const activeMandates = getActiveMandatesForSpecificDate(mandatesByDeputyId[deputy.id], date)
      const activeAssemblyMandates = _.filter(activeMandates, (mandate) => {
        return mandate.authority.authorityType === 'ASSEMBLEE'
      })
      return !_.isEmpty(activeAssemblyMandates)
    })

    return _.groupBy(activeDeputies, (deputy) => {
      const activeMandates = getActiveMandatesForSpecificDate(mandatesByDeputyId[deputy.id], date)
      const activePGMandates = _.filter(activeMandates, (mandate) => {
        return mandate.authority.authorityType === 'GP'
      })
      return _.last(_.sortBy(activePGMandates, (mandate) => mandate.startDate))?.authority.id
    })
  }
}
