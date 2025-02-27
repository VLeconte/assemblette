import { defineStore } from 'pinia'
import _ from 'lodash'
import type { Authority } from '@/entities/authority'
import axios from 'axios'

export const useAuthoritiesStore = defineStore('authorities', () => {
  const restAuthorities = axios.create({
    // better still, use env vars to define your URLs
    baseURL: 'http://localhost:8080/api/authorities',
  })
  const authorities: Authority[] = []

  async function getAuthorities(): Promise<Authority[]> {
    try {
      if (_.isEmpty(authorities)) {
        return (await restAuthorities.get('')).data
      }
      return authorities
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

  return { getAuthorities }
})
