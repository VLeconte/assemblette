import { defineStore } from 'pinia'
import _ from 'lodash'
import type { Deputy } from '@/entities/deputy'
import axios from 'axios'
import { ref } from 'vue'

export const useDeputiesStore = defineStore('deputies', () => {
  const restDeputies = axios.create({
    // better still, use env vars to define your URLs
    baseURL: 'http://localhost:8080/api/deputies',
  })
  let deputies: Deputy[] = []

  async function getDeputies(): Promise<Deputy[]> {
    try {
      if (_.isEmpty(deputies)) {
        deputies = (await restDeputies.get('')).data
      }
      return deputies
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

  const deputyIdSelectedOnHemicycle = ref('')

  return { getDeputies, deputyIdSelectedOnHemicycle }
})
