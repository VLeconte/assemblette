import { defineStore } from 'pinia'
import _ from 'lodash'
import type { Ballot } from '@/entities/ballot'
import axios from 'axios'

export const useBallotsStore = defineStore('ballots', () => {
  const restBallots = axios.create({
    // better still, use env vars to define your URLs
    baseURL: 'http://localhost:8080/api/ballots',
  })
  const ballots: Ballot[] = []

  async function getBallots() {
    try {
      if (_.isEmpty(ballots)) {
        return (await restBallots.get('')).data
      }
      return ballots
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

  return { getBallots }
})
