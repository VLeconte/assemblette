import type { Mandate } from '@/entities/mandate'
import { defineStore } from 'pinia'
import _ from 'lodash'
import axios from 'axios'

export const useMandatesStore = defineStore('mandates', () => {
  const restMandates = axios.create({
    // better still, use env vars to define your URLs
    baseURL: 'http://localhost:8080/api/mandates',
  })
  let mandates: Mandate[] = []
  let mandatesByDeputies: Record<string, Mandate[]> = {}

  async function getMandates() {
    if (_.isEmpty(mandates)) {
      try {
        mandates = (await restMandates.get('')).data
      } catch (err: unknown) {
        if (axios.isAxiosError(err)) {
          console.error(err.toJSON())
          throw new Error(err.message)
        } else {
          console.error(err)
          throw new Error('An unexpected error occurred')
        }
      }
    }
    return mandates
  }

  async function getMandatesByDeputies() {
    if (_.isEmpty(mandatesByDeputies)) {
      try {
        mandates = await getMandates()
        mandatesByDeputies = _.groupBy(mandates, (mandate) => mandate.deputy.id)
      } catch (error) {
        console.error('Error fetching deputies', error)
      }
    }
    return mandatesByDeputies
  }

  return { getMandates, getMandatesByDeputies }
})
